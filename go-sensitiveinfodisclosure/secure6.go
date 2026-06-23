package main

import (
	"context"
	"database/sql"
	"fmt"

	"github.com/tmc/langchaingo/llms/openai"
)

var db *sql.DB

func summarizeProduct(ctx context.Context, productID int) (string, error) {
	llm, _ := openai.New()
	var name, description string
	db.QueryRow("SELECT name, description FROM products WHERE id = ?", productID).Scan(&name, &description)
	return llm.Call(ctx, fmt.Sprintf("Summarize this product in one sentence: %s — %s", name, description))
}

func main() {
	result, _ := summarizeProduct(context.Background(), 1)
	fmt.Println(result)
}
