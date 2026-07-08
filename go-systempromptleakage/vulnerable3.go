package main

import (
	"context"
	"log/slog"

	openai "github.com/sashabaranov/go-openai"
)

var llmClient *openai.Client

const systemPrompt = "You are a support agent with access to internal ticket data and escalation paths."

func processQuery(ctx context.Context, userInput string) (string, error) {
	slog.Info("starting query", "system_prompt", systemPrompt)
	resp, err := llmClient.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: userInput},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}
