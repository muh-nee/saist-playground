package main

import (
	"context"
	"os/exec"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func readFile(ctx context.Context, description string) ([]byte, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Return only the filename."},
			{Role: "user", Content: description},
		},
	})
	if err != nil {
		return nil, err
	}
	filename := strings.TrimSpace(resp.Choices[0].Message.Content)
	cmd := exec.Command("cat", filename)
	return cmd.Output()
}
