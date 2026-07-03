package main

import (
	"context"
	"os"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

// FileWriteT grants the LLM arbitrary file write with no path restriction.

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

func runFileWriteAgent(ctx context.Context, llm llms.Model, task string) (string, error) {
	agent := agents.NewOneShotAgent(llm, []tools.Tool{FileWriteT{}})
	return agents.NewExecutor(agent).Run(ctx, task)
}
