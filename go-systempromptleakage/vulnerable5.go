package main

import (
	"context"
	"log"

	openai "github.com/sashabaranov/go-openai"
)

var llmClient *openai.Client

const systemPrompt = "You are a sales assistant. Internal pricing: Enterprise $10k/yr, Pro $1k/yr."

func handleRequest(ctx context.Context, userMsg string) (string, error) {
	log.Printf("handling request with systemPrompt=%s", systemPrompt)
	resp, err := llmClient.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
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


