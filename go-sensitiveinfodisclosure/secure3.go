package main

import (
	"context"
	"database/sql"
	"fmt"
	"os"

	"github.com/anthropics/anthropic-sdk-go"
)

var client = anthropic.NewClient()

func getDBHelp(ctx context.Context, errMsg string) (string, error) {
	dbPassword := os.Getenv("DB_PASSWORD")
	dbHost := os.Getenv("DB_HOST")
	connStr := fmt.Sprintf("postgresql://app:%s@%s/prod", dbPassword, dbHost)
	db, _ := sql.Open("postgres", connStr)
	defer db.Close()

	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaude3_5Sonnet20241022,
		MaxTokens: 512,
		Messages: []anthropic.MessageParam{
			{Role: "user", Content: fmt.Sprintf("Help fix this DB error: %s", errMsg)},
		},
	})
	if err != nil {
		return "", err
	}
	return msg.Content[0].Text, nil
}

func main() {
	result, _ := getDBHelp(context.Background(), "remaining connection slots are reserved")
	fmt.Println(result)
}
