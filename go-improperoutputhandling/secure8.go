package main

import (
	"context"
	"encoding/json"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func handleAnalysis(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()
	input := r.URL.Query().Get("input")
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: input},
		},
	})
	if err != nil {
		http.Error(w, "error", http.StatusInternalServerError)
		return
	}
	summary := resp.Choices[0].Message.Content
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{"summary": summary})
}
