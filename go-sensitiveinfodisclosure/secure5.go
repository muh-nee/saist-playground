package main

import (
	"context"
	"database/sql"
	"fmt"

	"github.com/tmc/langchaingo/llms/openai"
)

var db *sql.DB

func buildSupportContext(name, email, plan string) string {
	return fmt.Sprintf("Customer plan: %s.", plan)
}

func handleSupportTicket(ctx context.Context, userID int, issue string) (string, error) {
	llm, _ := openai.New()
	var name, email, plan string
	db.QueryRow("SELECT name, email, plan FROM users WHERE id = ?", userID).Scan(&name, &email, &plan)
	supportPrompt := buildSupportContext(name, email, plan)
	return llm.Call(ctx, fmt.Sprintf("%s Issue: %s", supportPrompt, issue))
}

func main() {
	result, _ := handleSupportTicket(context.Background(), 1, "cannot access account")
	fmt.Println(result)
}
