package main

import (
	"context"
	"os/exec"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

type ShellTool struct{}

func (t *ShellTool) Name() string        { return "RunCommand" }
func (t *ShellTool) Description() string { return "Run a shell command" }
func (t *ShellTool) Call(ctx context.Context, cmd string) (string, error) {
	out, err := exec.Command("sh", "-c", cmd).Output()
	return string(out), err
}

func runAgent(ctx context.Context, llm llms.Model, task string) (string, error) {
	agentTools := []tools.Tool{&ShellTool{}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(5))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
