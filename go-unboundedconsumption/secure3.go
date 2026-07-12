package main

import (
	"context"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

func classify(ctx context.Context, client *anthropic.Client, text string) (string, error) {
	resp, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.F(anthropic.ModelClaude3_5SonnetLatest),
		MaxTokens: anthropic.F(int64(1024)),
		Messages: anthropic.F([]anthropic.MessageParam{
			anthropic.NewUserMessage(anthropic.NewTextBlock(text)),
		}),
	})
	if err != nil {
		return "", err
	}
	return resp.Content[0].Text, nil
}
