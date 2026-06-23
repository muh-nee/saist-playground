package main

import (
	"context"
	"database/sql"
	"fmt"
	"os"

	"github.com/sashabaranov/go-openai"
)

var (
	db     *sql.DB
	client = openai.NewClient(os.Getenv("OPENAI_API_KEY"))
)

func summarizeUserActivity(ctx context.Context, userID int) (string, error) {
	var lastLogin string
	var loginCount int
	db.QueryRow("SELECT last_login, login_count FROM users WHERE id = ?", userID).Scan(&lastLogin, &loginCount)

	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: fmt.Sprintf("Summarize activity for user %d: last login %s, %d total logins.", userID, lastLogin, loginCount)},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}

func main() {
	result, _ := summarizeUserActivity(context.Background(), 1)
	fmt.Println(result)
}
