package main

import (
	"context"
	"encoding/json"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func handleSummary(client *openai.Client) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		resp, _ := client.CreateChatCompletion(
			context.Background(),
			openai.ChatCompletionRequest{
				Model: openai.GPT4oMini,
				Messages: []openai.ChatCompletionMessage{
					{Role: openai.ChatMessageRoleUser, Content: "Summarize the latest AI news in Markdown."},
				},
			},
		)
		output := resp.Choices[0].Message.Content
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(map[string]string{"content": output})
	}
}
