package main

import (
	"context"
	"os"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

type FileWriterTool struct{}

func (t *FileWriterTool) Name() string        { return "WriteFile" }
func (t *FileWriterTool) Description() string { return "Write content to a file" }
func (t *FileWriterTool) Call(ctx context.Context, input string) (string, error) {
	// input expected as "path:content"
	for i, ch := range input {
		if ch == ':' {
			path := input[:i]
			content := input[i+1:]
			err := os.WriteFile(path, []byte(content), 0644)
			if err != nil {
				return "", err
			}
			return "written", nil
		}
	}
	return "", nil
}

func runWriteAgent(ctx context.Context, llm llms.Model, task string) (string, error) {
	agentTools := []tools.Tool{&FileWriterTool{}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(3))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
