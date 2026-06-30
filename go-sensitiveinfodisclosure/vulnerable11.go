package main

import (
	"context"
	"fmt"
	"os"

	"github.com/sashabaranov/go-openai"
)

var aiClient = openai.NewClient(os.Getenv("OPENAI_API_KEY"))

func debugAuthError(ctx context.Context, errMsg string) (string, error) {
	apiSecret := os.Getenv("API_SECRET")
	resp, err := aiClient.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: fmt.Sprintf("Debug this auth error: %s. Secret in use: %s", errMsg, apiSecret)},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}
