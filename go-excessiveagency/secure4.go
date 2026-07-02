package main

import (
	"context"
	"errors"
	"io"
	"net/http"
	"net/url"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

var allowedHosts = map[string]bool{
	"api.internal.example.com": true,
}

func fetchInternal(rawURL string) (string, error) {
	parsed, err := url.Parse(rawURL)
	if err != nil || !allowedHosts[parsed.Hostname()] {
		return "", errors.New("URL not in allowed domains")
	}
	resp, err := http.Get(rawURL)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()
	body, _ := io.ReadAll(resp.Body)
	return string(body), nil
}

func handleSafeAnthropicRequest(ctx context.Context, messages []anthropic.MessageParam) error {
	client := anthropic.NewClient()
	_, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.F(anthropic.ModelClaude3_5SonnetLatest),
		MaxTokens: anthropic.F(int64(1024)),
		Tools: anthropic.F([]anthropic.ToolParam{
			{
				Name:        anthropic.F("fetch_internal"),
				Description: anthropic.F("Fetch data from the internal reporting API"),
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
