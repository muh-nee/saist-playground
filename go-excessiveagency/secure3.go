package main

import (
	"context"
	"errors"
	"os/exec"
	"strings"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

var allowedCommands = map[string]bool{
	"df":       true,
	"free":     true,
	"uptime":   true,
	"hostname": true,
}

type DiagnosticTool struct{}

func (t *DiagnosticTool) Name() string        { return "RunDiagnostic" }
func (t *DiagnosticTool) Description() string { return "Run an approved system diagnostic command" }
func (t *DiagnosticTool) Call(ctx context.Context, cmd string) (string, error) {
	parts := strings.Fields(cmd)
	if len(parts) == 0 || !allowedCommands[parts[0]] {
		return "", errors.New("command not in allowlist")
	}
	out, err := exec.Command(parts[0], parts[1:]...).Output()
	return string(out), err
}

func runDiagAgent(ctx context.Context, llm llms.Model, task string) (string, error) {
	agentTools := []tools.Tool{&DiagnosticTool{}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(3))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
