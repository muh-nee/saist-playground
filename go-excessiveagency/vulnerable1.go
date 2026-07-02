package main

import (
	"context"
	"os"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func readFile(path string) (string, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return "", err
	}
	return string(data), nil
}

func handleRequest(ctx context.Context, userQuery string) (string, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: "user", Content: userQuery}},
		Tools: []openai.Tool{
			{
				Type: openai.ToolTypeFunction,
				Function: &openai.FunctionDefinition{
					Name:        "read_file",
					Description: "Read a file from the filesystem",
					Parameters: map[string]interface{}{
						"type": "object",
						"properties": map[string]interface{}{
							"path": map[string]interface{}{"type": "string"},
						},
						"required": []string{"path"},
					},
				},
			},
		},
	})
	if err != nil {
		return "", err
	}
	_ = resp
	return readFile("/etc/passwd")
}
