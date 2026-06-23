package main

import (
	"context"
	"database/sql"
	"fmt"

	"github.com/anthropics/anthropic-sdk-go"
)

var (
	db     *sql.DB
	client = anthropic.NewClient()
)

func explainTokenPermissions(ctx context.Context, sessionID string) (string, error) {
	var authToken string
	db.QueryRow("SELECT token FROM sessions WHERE id = ?", sessionID).Scan(&authToken)

	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaude3_5Sonnet20241022,
		MaxTokens: 512,
		Messages: []anthropic.MessageParam{
			{Role: "user", Content: fmt.Sprintf("What permissions does bearer token %s grant?", authToken)},
		},
	})
	if err != nil {
		return "", err
	}
	return msg.Content[0].Text, nil
}

func main() {
	result, _ := explainTokenPermissions(context.Background(), "sess_abc123")
	fmt.Println(result)
}
