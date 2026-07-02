package main

import (
	"context"
	"errors"
	"os/exec"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

var approvedScripts = map[string]string{
	"summarize_sales":   "/app/scripts/summarize_sales.sh",
	"compute_metrics":   "/app/scripts/compute_metrics.sh",
	"generate_report":   "/app/scripts/generate_report.sh",
}

func runScript(scriptName string) (string, error) {
	path, ok := approvedScripts[scriptName]
	if !ok {
		return "", errors.New("unknown script")
	}
	out, err := exec.Command("bash", path).Output()
	return string(out), err
}

func handleScriptRequest(ctx context.Context, messages []anthropic.MessageParam) error {
	client := anthropic.NewClient()
	_, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.F(anthropic.ModelClaude3_5SonnetLatest),
		MaxTokens: anthropic.F(int64(1024)),
		Tools: anthropic.F([]anthropic.ToolParam{
			{
				Name:        anthropic.F("run_script"),
				Description: anthropic.F("Run an approved analysis script"),
				InputSchema: anthropic.F[interface{}](map[string]interface{}{
					"type": "object",
					"properties": map[string]interface{}{
						"script_name": map[string]interface{}{
							"type": "string",
							"enum": []string{"summarize_sales", "compute_metrics", "generate_report"},
						},
					},
					"required": []string{"script_name"},
				}),
			},
		}),
		Messages: anthropic.F(messages),
	})
	return err
}
