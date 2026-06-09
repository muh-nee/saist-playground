package main

import (
	"context"
	"strings"

	openai "github.com/sashabaranov/go-openai"
	"gorm.io/gorm"
)

var gdb *gorm.DB
var client *openai.Client

type Report struct {
	ID    uint
	Title string
	Body  string
}

func runReport(ctx context.Context, userQuery string) ([]Report, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Translate the user's question into a SQL query against the reports table."},
			{Role: "user", Content: userQuery},
		},
	})
	if err != nil {
		return nil, err
	}
	query := strings.TrimSpace(resp.Choices[0].Message.Content)
	var reports []Report
	if err := gdb.Raw(query).Scan(&reports).Error; err != nil {
		return nil, err
	}
	return reports, nil
}
