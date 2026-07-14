package main

import (
	"context"

	"github.com/sirupsen/logrus"
	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "Internal HR assistant. Access to salary ranges and headcount data for all departments."

func processRequest(ctx context.Context, userInput string) (string, error) {
	logrus.WithField("prompt", systemPrompt).Info("processing LLM request")
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:     openai.GPT4o,
		MaxTokens: 1024,
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
