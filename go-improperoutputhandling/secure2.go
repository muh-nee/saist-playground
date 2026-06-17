package main

import (
	"context"
	"database/sql"

	openai "github.com/sashabaranov/go-openai"
)

var db *sql.DB
var client *openai.Client

func searchUsers(ctx context.Context, prompt string) (*sql.Rows, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: prompt},
		},
	})
	if err != nil {
		return nil, err
	}
	searchTerm := resp.Choices[0].Message.Content
	return db.QueryContext(ctx, "SELECT * FROM users WHERE name = ?", searchTerm)
}

