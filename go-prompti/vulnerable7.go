package main

import (
	"context"

	openai "github.com/sashabaranov/go-openai"
)

func agentTurn(ctx context.Context, client *openai.Client, messages []openai.ChatCompletionMessage, toolOutput string) (string, error) {
	messages = append(messages, openai.ChatCompletionMessage{
		Role:    openai.ChatMessageRoleUser,
		Content: toolOutput,
	})
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: messages,
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}
