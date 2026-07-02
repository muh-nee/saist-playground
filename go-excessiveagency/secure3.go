package main

import (
	"context"
	"errors"
	"os/exec"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

// Full-argv tuple allowlist: both the command name AND its arguments are
// bound. The LLM selects a diagnostic key; the process invocation is fixed.
var diagnosticInvocations = map[string][]string{
	"disk":     {"df", "-h"},
	"memory":   {"free", "-m"},
	"uptime":   {"uptime"},
	"hostname": {"hostname"},
}

type DiagnosticTool struct{}

func (t *DiagnosticTool) Name() string { return "RunDiagnostic" }
func (t *DiagnosticTool) Description() string {
	return "Run an approved diagnostic: disk, memory, uptime, or hostname"
}
func (t *DiagnosticTool) Call(ctx context.Context, name string) (string, error) {
	argv, ok := diagnosticInvocations[name]
	if !ok {
		return "", errors.New("diagnostic not in allowlist")
	}
	out, err := exec.Command(argv[0], argv[1:]...).Output()
	return string(out), err
}

func runDiagAgent(ctx context.Context, llm llms.Model, task string) (string, error) {
	agentTools := []tools.Tool{&DiagnosticTool{}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(3))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
