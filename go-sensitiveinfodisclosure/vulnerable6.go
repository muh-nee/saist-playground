package main

import (
	"context"
	"database/sql"
	"fmt"

	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/llms/openai"
	"github.com/tmc/langchaingo/schema"
)

var db *sql.DB

func reviewAccount(ctx context.Context, userID int) error {
	llm, _ := openai.New()
	var email, creditCard string
	db.QueryRow("SELECT email, credit_card FROM users WHERE id = ?", userID).Scan(&email, &creditCard)

	_, err := llm.GenerateContent(ctx, []llms.MessageContent{
		{Role: schema.ChatMessageTypeHuman, Parts: []llms.ContentPart{
			llms.TextPart(fmt.Sprintf("Review account for %s (card: %s)", email, creditCard)),
		}},
	})
	return err
}

func main() {
	reviewAccount(context.Background(), 1)
}
