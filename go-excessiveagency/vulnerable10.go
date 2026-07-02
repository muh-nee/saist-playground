package main

import (
	"context"
	"database/sql"
	"io"
	"net/http"
	"os"
	"os/exec"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

// Composition risk: a single agent is granted many powerful tools with no
// guardrails. Even if each tool were bounded on its own, the combination
// gives the LLM far more agency than the task requires.

type ShellT struct{}

func (ShellT) Name() string        { return "Shell" }
func (ShellT) Description() string { return "Run a shell command" }
func (ShellT) Call(ctx context.Context, cmd string) (string, error) {
	out, err := exec.Command("sh", "-c", cmd).Output()
	return string(out), err
}

type FileWriteT struct{}

func (FileWriteT) Name() string        { return "WriteFile" }
func (FileWriteT) Description() string { return "Write bytes to a file" }
func (FileWriteT) Call(ctx context.Context, input string) (string, error) {
	for i, ch := range input {
		if ch == ':' {
			return "", os.WriteFile(input[:i], []byte(input[i+1:]), 0644)
		}
	}
	return "", nil
}

type FileReadT struct{}

func (FileReadT) Name() string        { return "ReadFile" }
func (FileReadT) Description() string { return "Read a file" }
func (FileReadT) Call(ctx context.Context, path string) (string, error) {
	b, err := os.ReadFile(path)
	return string(b), err
}

type HTTPT struct{}

func (HTTPT) Name() string        { return "HTTPGet" }
func (HTTPT) Description() string { return "Fetch a URL" }
func (HTTPT) Call(ctx context.Context, u string) (string, error) {
	resp, err := http.Get(u)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()
	b, _ := io.ReadAll(resp.Body)
	return string(b), nil
}

type SQLT struct{ db *sql.DB }

func (t SQLT) Name() string        { return "SQL" }
func (t SQLT) Description() string { return "Execute SQL" }
func (t SQLT) Call(ctx context.Context, q string) (string, error) {
	_, err := t.db.ExecContext(ctx, q)
	return "ok", err
}

type EnvT struct{}

func (EnvT) Name() string        { return "ReadEnv" }
func (EnvT) Description() string { return "Read environment variable" }
func (EnvT) Call(ctx context.Context, name string) (string, error) {
	return os.Getenv(name), nil
}

func runOmnibusAgent(ctx context.Context, llm llms.Model, db *sql.DB, task string) (string, error) {
	agentTools := []tools.Tool{
		ShellT{}, FileWriteT{}, FileReadT{}, HTTPT{}, SQLT{db: db}, EnvT{},
	}
	agent := agents.NewOneShotAgent(llm, agentTools)
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
