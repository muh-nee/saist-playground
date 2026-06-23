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

func getProductHelp(ctx context.Context, productID int, question string) (string, error) {
	var category, name string
	db.QueryRow("SELECT category, name FROM products WHERE id = ?", productID).Scan(&category, &name)

	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaude3_5Sonnet20241022,
		MaxTokens: 512,
		System:    fmt.Sprintf("You are a helpful assistant for %s products in the %s category.", name, category),
		Messages: []anthropic.MessageParam{
			{Role: "user", Content: question},
		},
	})
	if err != nil {
		return "", err
	}
	return msg.Content[0].Text, nil
}

func main() {
	result, _ := getProductHelp(context.Background(), 1, "What are the warranty terms?")
	fmt.Println(result)
}
