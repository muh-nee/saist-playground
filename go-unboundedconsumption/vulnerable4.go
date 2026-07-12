package main

import (
	"context"

	openai "github.com/sashabaranov/go-openai"
)

func streamAnswer(ctx context.Context, client *openai.Client, prompt string) error {
	stream, err := client.CreateChatCompletionStream(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleUser, Content: prompt},
		},
	})
	if err != nil {
		return err
	}
	defer stream.Close()
	for {
		_, err := stream.Recv()
		if err != nil {
			return err
		}
	}
}
