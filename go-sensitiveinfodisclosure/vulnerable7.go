package main

import (
	"context"
	"fmt"
	"os"

	"github.com/sashabaranov/go-openai"
)

const webhookSecret = "whsec_abc123xyz789secretkey"

var client = openai.NewClient(os.Getenv("OPENAI_API_KEY"))

func debugWebhookFailure(ctx context.Context, errMsg string) (string, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: fmt.Sprintf("Webhook verification failed: %s. Secret in use: %s", errMsg, webhookSecret)},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}

func main() {
	result, _ := debugWebhookFailure(context.Background(), "signature mismatch")
	fmt.Println(result)
}
