package main

import (
	"context"
	"crypto/sha256"
	"fmt"
	"log/slog"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "Internal assistant. Confidential instructions including escalation paths."

func handleQuery(ctx context.Context, userMsg string) (string, error) {
	hash := fmt.Sprintf("%x", sha256.Sum256([]byte(systemPrompt)))[:8]
	slog.Info("processing query", "prompt_hash", hash, "prompt_length", len(systemPrompt))
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
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
