package main

import (
	"context"
	"errors"
	"os"
	"path/filepath"
	"strings"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

const allowedRoot = "/var/app/data"

type SafeFileReaderTool struct{}

func (t *SafeFileReaderTool) Name() string        { return "ReadFile" }
func (t *SafeFileReaderTool) Description() string { return "Read a file from the data directory" }
func (t *SafeFileReaderTool) Call(ctx context.Context, path string) (string, error) {
	absPath, err := filepath.Abs(path)
	if err != nil || !strings.HasPrefix(absPath, allowedRoot+string(os.PathSeparator)) {
		return "", errors.New("path outside allowed directory")
	}
	data, err := os.ReadFile(absPath)
	return string(data), err
}

func runSafeFileAgent(ctx context.Context, llm llms.Model, task string) (string, error) {
	agentTools := []tools.Tool{&SafeFileReaderTool{}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(3))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
