package main

import (
	"context"
	"fmt"
	"os"

	openai "github.com/sashabaranov/go-openai"
)

func processTask(client *openai.Client, task string) string {
	resp, _ := client.CreateChatCompletion(
		context.Background(),
		openai.ChatCompletionRequest{
			Model: openai.GPT4oMini,
			Messages: []openai.ChatCompletionMessage{
				{Role: openai.ChatMessageRoleUser, Content: task},
			},
		},
	)
	output := resp.Choices[0].Message.Content
	fmt.Fprintln(os.Stdout, output)
	return output
}
