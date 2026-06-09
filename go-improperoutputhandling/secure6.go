package main

import (
	"context"
	"fmt"
	"os"
	"path/filepath"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const baseDir = "/var/app/docs"

func openDocSafe(ctx context.Context, description string) ([]byte, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Return only the filename that best matches the description."},
			{Role: "user", Content: description},
		},
	})
	if err != nil {
		return nil, err
	}
	filename := strings.TrimSpace(resp.Choices[0].Message.Content)
	fullPath := filepath.Join(baseDir, filepath.Clean("/"+filename))
	if !strings.HasPrefix(fullPath, baseDir+string(os.PathSeparator)) {
		return nil, fmt.Errorf("path escapes base directory")
	}
	return os.ReadFile(fullPath)
}
