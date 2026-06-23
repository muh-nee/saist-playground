package main

import (
	"context"
	"fmt"
	"os"

	"github.com/sashabaranov/go-openai"
)

var client = openai.NewClient(os.Getenv("OPENAI_API_KEY"))

func debugConnectionError(ctx context.Context, errMsg string) (string, error) {
	dbPassword := os.Getenv("DB_PASSWORD")
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: fmt.Sprintf("Debug this DB error: %s. Password in use: %s", errMsg, dbPassword)},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}

func main() {
	result, _ := debugConnectionError(context.Background(), "connection refused")
	fmt.Println(result)
}
