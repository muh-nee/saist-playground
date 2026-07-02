package main

import (
	"context"
	"io"
	"net/http"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

func fetchURL(rawURL string) (string, error) {
	resp, err := http.Get(rawURL)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()
	body, _ := io.ReadAll(resp.Body)
	return string(body), nil
}

func handleAnthropicRequest(ctx context.Context, messages []anthropic.MessageParam) error {
	client := anthropic.NewClient()
	_, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.F(anthropic.ModelClaude3_5SonnetLatest),
		MaxTokens: anthropic.F(int64(1024)),
		Tools: anthropic.F([]anthropic.ToolParam{
			{
				Name:        anthropic.F("fetch_url"),
				Description: anthropic.F("Fetch the content of a URL"),
				InputSchema: anthropic.F[interface{}](map[string]interface{}{
					"type": "object",
					"properties": map[string]interface{}{
						"url": map[string]interface{}{"type": "string"},
					},
					"required": []string{"url"},
				}),
			},
		}),
		Messages: anthropic.F(messages),
	})
	return err
}
