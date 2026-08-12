package main

import (
	"context"
	"fmt"
	"os"
	"regexp"

	openai "github.com/sashabaranov/go-openai"
)

var ansiEscape = regexp.MustCompile(`\x1b\[[0-9;]*m`)

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
	clean := ansiEscape.ReplaceAllString(output, "")
	fmt.Fprintln(os.Stdout, clean)
	return clean
}
