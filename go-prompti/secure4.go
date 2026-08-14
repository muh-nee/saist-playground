package main

import (
	"context"
	"fmt"

	openai "github.com/sashabaranov/go-openai"
)

const staticSystemPrompt = "You are a helpful search assistant."

type SearchResult struct {
	ResultCount int `json:"result_count"`
}

func agentTurnSafe(ctx context.Context, client *openai.Client, messages []openai.ChatCompletionMessage, toolResult SearchResult) (string, error) {
	safeContent := fmt.Sprintf("Found %d results", toolResult.ResultCount)
	messages = append(messages, openai.ChatCompletionMessage{
		Role:    openai.ChatMessageRoleUser,
		Content: safeContent,
	})
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: append(
			[]openai.ChatCompletionMessage{{Role: openai.ChatMessageRoleSystem, Content: staticSystemPrompt}},
			messages...,
		),
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}
