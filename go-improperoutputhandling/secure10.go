package main

import (
	"context"
	"log"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func logInsight(ctx context.Context, topic string) error {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: topic},
		},
	})
	if err != nil {
		return err
	}
	insight := resp.Choices[0].Message.Content
	log.Printf("insight: %s", insight)
	return nil
}
