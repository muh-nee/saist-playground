package main

import (
	"context"
	"os/exec"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

func runDiagnostic(cmd string) (string, error) {
	out, err := exec.Command("sh", "-c", cmd).Output()
	return string(out), err
}

func handleDiagnosticRequest(ctx context.Context, messages []anthropic.MessageParam) error {
	client := anthropic.NewClient()
	_, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.F(anthropic.ModelClaude3_5SonnetLatest),
		MaxTokens: anthropic.F(int64(1024)),
		Tools: anthropic.F([]anthropic.ToolParam{
			{
				Name:        anthropic.F("run_diagnostic"),
				Description: anthropic.F("Run a diagnostic command on the host"),
				InputSchema: anthropic.F[interface{}](map[string]interface{}{
					"type": "object",
					"properties": map[string]interface{}{
						"cmd": map[string]interface{}{"type": "string"},
					},
					"required": []string{"cmd"},
				}),
			},
		}),
		Messages: anthropic.F(messages),
	})
	return err
}
