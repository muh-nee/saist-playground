package main

import (
	"context"

	openai "github.com/sashabaranov/go-openai"
)

type Config struct {
	MaxTokens int
}

func answerWithConfig(ctx context.Context, client *openai.Client, cfg Config, prompt string) (string, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:     openai.GPT4o,
		MaxTokens: cfg.MaxTokens,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleUser, Content: prompt},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}
