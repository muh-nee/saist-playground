package main

import (
	"context"
	"encoding/json"
	"fmt"
	"os/exec"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

type FileOperation struct {
	Action string `json:"action"`
	Path   string `json:"path"`
}

var allowedActions = map[string]bool{
	"list": true,
	"read": true,
	"size": true,
}

func handleFileOp(ctx context.Context, prompt string) ([]byte, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Reply with a JSON object with fields: action (one of list, read, size) and path."},
			{Role: "user", Content: prompt},
		},
	})
	if err != nil {
		return nil, err
	}
	var op FileOperation
	if err := json.Unmarshal([]byte(resp.Choices[0].Message.Content), &op); err != nil {
		return nil, err
	}
	if !allowedActions[op.Action] {
		return nil, fmt.Errorf("disallowed action: %s", op.Action)
	}
	cmd := exec.Command("ls", op.Path)
	return cmd.Output()
}
