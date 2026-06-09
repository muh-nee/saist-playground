package main

import (
	"context"
	"database/sql"

	openai "github.com/sashabaranov/go-openai"
)

var db *sql.DB
var client *openai.Client

func getReport(ctx context.Context, userQuery string) (*sql.Rows, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: userQuery},
		},
	})
	if err != nil {
		return nil, err
	}
	sqlQuery := resp.Choices[0].Message.Content
	return db.QueryContext(ctx, sqlQuery)
}
