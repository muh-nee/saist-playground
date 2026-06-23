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

func buildSupportContext(name, email, account string) string {
	return fmt.Sprintf("Customer: %s, email: %s, account: %s", name, email, account)
}

func handleSupportTicket(ctx context.Context, userID int, issue string) (string, error) {
	var name, email, account string
	db.QueryRow("SELECT name, email, account FROM users WHERE id = ?", userID).Scan(&name, &email, &account)
	supportPrompt := buildSupportContext(name, email, account)

	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaude3_5Sonnet20241022,
		MaxTokens: 512,
		Messages: []anthropic.MessageParam{
			{Role: "user", Content: fmt.Sprintf("%s. Issue: %s", supportPrompt, issue)},
		},
	})
	if err != nil {
		return "", err
	}
	return msg.Content[0].Text, nil
}

func main() {
	result, _ := handleSupportTicket(context.Background(), 1, "cannot access account")
	fmt.Println(result)
}
