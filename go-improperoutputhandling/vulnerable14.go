package main

import (
	"context"
	"net/http"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func redirectToRecommended(w http.ResponseWriter, r *http.Request) {
	ctx := context.Background()
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Return the best URL for the user to visit next."},
			{Role: "user", Content: r.URL.Query().Get("intent")},
		},
	})
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	target := strings.TrimSpace(resp.Choices[0].Message.Content)
	http.Redirect(w, r, target, http.StatusFound)
}
