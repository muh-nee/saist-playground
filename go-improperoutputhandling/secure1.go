package main

import (
	"context"
	"database/sql"
	"fmt"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var db *sql.DB
var client *openai.Client

var allowedTables = map[string]bool{
	"users":    true,
	"orders":   true,
	"products": true,
}

func queryTable(ctx context.Context, userQuestion string) (*sql.Rows, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Reply with only the table name relevant to the question."},
			{Role: "user", Content: userQuestion},
		},
	})
	if err != nil {
		return nil, err
	}
	tableName := strings.TrimSpace(strings.ToLower(resp.Choices[0].Message.Content))
	if !allowedTables[tableName] {
		return nil, fmt.Errorf("unknown table: %s", tableName)
	}
	return db.QueryContext(ctx, fmt.Sprintf("SELECT * FROM %s WHERE active = ?", tableName), 1)
}
