package main

import (
	"bytes"
	"context"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
	"github.com/yuin/goldmark"
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
		var rendered bytes.Buffer
		if err := goldmark.Convert([]byte(output), &rendered); err != nil {
			http.Error(w, "render failed", http.StatusInternalServerError)
			return
		}
		w.Header().Set("Content-Type", "text/html")
		w.Write(rendered.Bytes())
	}
}
