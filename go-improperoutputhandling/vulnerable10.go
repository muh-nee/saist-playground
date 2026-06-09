package main

import (
	"context"
	"net/http"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func fetchResource(ctx context.Context, description string) (*http.Response, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Return only the URL for the resource described."},
			{Role: "user", Content: description},
		},
	})
	if err != nil {
		return nil, err
	}
	url := strings.TrimSpace(resp.Choices[0].Message.Content)
	return http.Get(url)
}
