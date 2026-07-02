package main

import (
	"context"
	"os"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

type FileReaderTool struct{}

func (t *FileReaderTool) Name() string        { return "ReadFile" }
func (t *FileReaderTool) Description() string { return "Read a file from the filesystem" }
func (t *FileReaderTool) Call(ctx context.Context, path string) (string, error) {
	data, err := os.ReadFile(path)
	return string(data), err
}

func runFileAgent(ctx context.Context, llm llms.Model, task string) (string, error) {
	agentTools := []tools.Tool{&FileReaderTool{}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(3))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
