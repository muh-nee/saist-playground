package main

import (
	"context"
	"io"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var oaiClient *openai.Client

func httpFetch(rawURL string) (string, error) {
	resp, err := http.Get(rawURL)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()
	body, _ := io.ReadAll(resp.Body)
	return string(body), nil
}

func handleFetchRequest(ctx context.Context, userQuery string) (string, error) {
	_, err := oaiClient.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: "user", Content: userQuery}},
		Tools: []openai.Tool{
			{
				Type: openai.ToolTypeFunction,
				Function: &openai.FunctionDefinition{
					Name:        "http_fetch",
					Description: "Fetch data from a URL",
					Parameters: map[string]interface{}{
						"type": "object",
						"properties": map[string]interface{}{
							"url": map[string]interface{}{"type": "string"},
						},
						"required": []string{"url"},
					},
				},
			},
		},
	})
	return "", err
}
