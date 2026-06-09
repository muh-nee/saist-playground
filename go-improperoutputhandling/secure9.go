package main

import (
	"context"
	"database/sql"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var db *sql.DB
var client *openai.Client

func handleUserLookup(ctx context.Context, w http.ResponseWriter, r *http.Request) {
	userID := r.URL.Query().Get("id")

	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "You are a helpful assistant."},
			{Role: "user", Content: "What fields are in the users table?"},
		},
	})
	if err != nil {
		http.Error(w, "error", http.StatusInternalServerError)
		return
	}
	_ = resp.Choices[0].Message.Content

	db.QueryContext(ctx, "SELECT * FROM users WHERE id = '"+userID+"'")
}
