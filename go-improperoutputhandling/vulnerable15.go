package main

import (
	"context"
	"encoding/json"
	"os/exec"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

func runWithTools(ctx context.Context, userPrompt string) ([]byte, error) {
	client := anthropic.NewClient()
	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaudeSonnet4_6,
		MaxTokens: 1024,
		Tools: []anthropic.ToolUnionParam{
			{
				OfTool: &anthropic.ToolParam{
					Name:        "run_shell",
					Description: anthropic.String("Run a shell command and return its output."),
					InputSchema: anthropic.ToolInputSchemaParam{
						Properties: map[string]any{
							"command": map[string]any{"type": "string"},
						},
					},
				},
			},
		},
		Messages: []anthropic.MessageParam{
			anthropic.NewUserMessage(anthropic.NewTextBlock(userPrompt)),
		},
	})
	if err != nil {
		return nil, err
	}
	for _, block := range msg.Content {
		if block.Type == "tool_use" && block.Name == "run_shell" {
			var args struct {
				Command string `json:"command"`
			}
			if err := json.Unmarshal([]byte(block.Input), &args); err != nil {
				return nil, err
			}
			return exec.Command("sh", "-c", args.Command).Output()
		}
	}
	return nil, nil
}
