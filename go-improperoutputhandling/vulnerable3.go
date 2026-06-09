package main

import (
	"context"
	"os/exec"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func runTask(ctx context.Context, taskDescription string) error {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: taskDescription},
		},
	})
	if err != nil {
		return err
	}
	command := resp.Choices[0].Message.Content
	cmd := exec.Command("sh", "-c", command)
	return cmd.Run()
}
