package main

import (
	"context"

	"go.uber.org/zap"
	openai "github.com/sashabaranov/go-openai"
)

var (
	client *openai.Client
	logger *zap.Logger
)

const systemPrompt = "You are a customer support bot. Internal note: escalate VIP tier customers only."

func handleQuery(ctx context.Context, userMsg string) (string, error) {
	logger.Info("processing query", zap.String("system_prompt", systemPrompt))
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:     openai.GPT4o,
		MaxTokens: 1024,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: userMsg},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}
