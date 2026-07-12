package main

import (
	"context"

	openai "github.com/sashabaranov/go-openai"
)

func draftEmail(ctx context.Context, client *openai.Client, topic string) (string, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: openai.ChatMessageRoleUser, Content: "Draft an email about: " + topic}},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}

func reviewEmail(ctx context.Context, client *openai.Client, draft string) (string, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: openai.ChatMessageRoleUser, Content: "Review this email: " + draft}},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}
