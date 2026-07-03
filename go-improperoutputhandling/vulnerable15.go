package main

import (
	"context"
	"os/exec"
	"strings"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

// The LLM's text response is used directly as a shell command with no sanitization.

func runLLMCommand(ctx context.Context, userRequest string) ([]byte, error) {
	client := anthropic.NewClient()
	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaudeSonnet4_6,
		MaxTokens: 256,
		Messages: []anthropic.MessageParam{
			anthropic.NewUserMessage(anthropic.NewTextBlock(userRequest)),
		},
	})
	if err != nil {
		return nil, err
	}
	command := strings.TrimSpace(msg.Content[0].Text)
	return exec.Command("sh", "-c", command).Output()
}
