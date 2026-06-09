package main

import (
	"context"
	"net/http"
	"strings"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

func proxyRequest(ctx context.Context, userIntent string) (*http.Response, error) {
	client := anthropic.NewClient()
	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaudeSonnet4_6,
		MaxTokens: 256,
		Messages: []anthropic.MessageParam{
			anthropic.NewUserMessage(anthropic.NewTextBlock(userIntent)),
		},
	})
	if err != nil {
		return nil, err
	}
	targetURL := strings.TrimSpace(msg.Content[0].Text)
	req, err := http.NewRequestWithContext(ctx, http.MethodGet, targetURL, nil)
	if err != nil {
		return nil, err
	}
	return http.DefaultClient.Do(req)
}
