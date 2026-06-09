package main

import (
	"context"
	"fmt"
	"os/exec"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

var allowedCommands = map[string]bool{
	"uptime": true,
	"date":   true,
	"whoami": true,
}

func runSystemCommand(ctx context.Context, prompt string) ([]byte, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Reply with only the command name (no arguments)."},
			{Role: "user", Content: prompt},
		},
	})
	if err != nil {
		return nil, err
	}
	cmdName := strings.TrimSpace(resp.Choices[0].Message.Content)
	if !allowedCommands[cmdName] {
		return nil, fmt.Errorf("command not permitted: %s", cmdName)
	}
	cmd := exec.Command(cmdName)
	return cmd.Output()
}
