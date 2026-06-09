package main

import (
	"context"
	"io"
	"os"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func openDoc(ctx context.Context, description string) (string, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Return only the filename that best matches the description."},
			{Role: "user", Content: description},
		},
	})
	if err != nil {
		return "", err
	}
	filename := strings.TrimSpace(resp.Choices[0].Message.Content)
	f, err := os.Open("/var/app/docs/" + filename)
	if err != nil {
		return "", err
	}
	defer f.Close()
	data, err := io.ReadAll(f)
	if err != nil {
		return "", err
	}
	return string(data), nil
}
