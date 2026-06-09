package main

import (
	"context"
	"database/sql"
	"strings"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

var db *sql.DB

func applyInstruction(ctx context.Context, instruction string) error {
	client := anthropic.NewClient()
	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaudeSonnet4_6,
		MaxTokens: 256,
		Messages: []anthropic.MessageParam{
			anthropic.NewUserMessage(anthropic.NewTextBlock(instruction)),
		},
	})
	if err != nil {
		return err
	}
	query := strings.TrimSpace(msg.Content[0].Text)
	_, err = db.Exec(query)
	return err
}
