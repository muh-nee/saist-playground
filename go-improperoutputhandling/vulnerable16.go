package main

import (
	"context"
	"io"
	"os/exec"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var streamClient *openai.Client

func streamAndExecute(ctx context.Context, userPrompt string) error {
	stream, err := streamClient.CreateChatCompletionStream(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Return the shell command to run."},
			{Role: "user", Content: userPrompt},
		},
	})
	if err != nil {
		return err
	}
	defer stream.Close()

	var accumulated strings.Builder
	for {
		resp, err := stream.Recv()
		if err == io.EOF {
			break
		}
		if err != nil {
			return err
		}
		accumulated.WriteString(resp.Choices[0].Delta.Content)
	}

	command := strings.TrimSpace(accumulated.String())
	cmd := exec.Command("sh", "-c", command)
	return cmd.Run()
}
