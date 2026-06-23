package main

import (
	"context"
	"crypto/sha256"
	"database/sql"
	"fmt"
	"os"

	"github.com/sashabaranov/go-openai"
)

var (
	db     *sql.DB
	client = openai.NewClient(os.Getenv("OPENAI_API_KEY"))
)

func analyzeUserActivity(ctx context.Context, userID int) (string, error) {
	var email string
	db.QueryRow("SELECT email FROM users WHERE id = ?", userID).Scan(&email)

	h := sha256.Sum256([]byte(email))
	emailHash := fmt.Sprintf("%x", h)[:8]

	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: fmt.Sprintf("Analyze activity for user hash %s (ID: %d).", emailHash, userID)},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}

func main() {
	result, _ := analyzeUserActivity(context.Background(), 1)
	fmt.Println(result)
}
