package main

import (
	"context"
	"fmt"
	"os"

	"github.com/sashabaranov/go-openai"
)

func summarizeDocument(ctx context.Context, text string) (string, error) {
	client := openai.NewClient(os.Getenv("OPENAI_API_KEY"))
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Summarize the following document concisely."},
			{Role: "user", Content: text},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}

func main() {
	result, _ := summarizeDocument(context.Background(), "Quarterly earnings were up 12% year over year.")
	fmt.Println(result)
}
