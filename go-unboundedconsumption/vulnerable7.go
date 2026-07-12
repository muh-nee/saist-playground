package main

import (
	"context"

	openai "github.com/sashabaranov/go-openai"
)

func callLLMNoTimeout(client *openai.Client, prompt string) (string, error) {
	resp, err := client.CreateChatCompletion(context.TODO(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleUser, Content: prompt},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}
