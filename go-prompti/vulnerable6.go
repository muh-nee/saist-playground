package main

import (
	"context"

	openai "github.com/sashabaranov/go-openai"
)

func summarizeAndStore(ctx context.Context, client *openai.Client, userQuery, sessionID string) error {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleUser, Content: userQuery},
		},
	})
	if err != nil {
		return err
	}
	llmOutput := resp.Choices[0].Message.Content
	return vectorStore.AddDocuments(ctx, []Document{{ID: sessionID, Content: llmOutput}})
}
