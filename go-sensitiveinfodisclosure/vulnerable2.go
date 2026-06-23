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
	var email, ssn string
	db.QueryRow("SELECT email, ssn FROM users WHERE id = ?", userID).Scan(&email, &ssn)

	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: fmt.Sprintf("Summarize activity for %s (SSN: %s)", email, ssn)},
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
