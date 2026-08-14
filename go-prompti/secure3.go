package main

import (
	"context"
	"regexp"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var (
	injectionRe = regexp.MustCompile(`(?i)(ignore (all |previous )?instructions?|you are now|system:)`)
	controlRe   = regexp.MustCompile(`<\|[^|]*\|>`)
)

func sanitizeBeforeStorage(text string) string {
	text = injectionRe.ReplaceAllString(text, "")
	text = controlRe.ReplaceAllString(text, "")
	return strings.TrimSpace(text)
}

func summarizeAndStoreSafe(ctx context.Context, client *openai.Client, userQuery, sessionID string) error {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleUser, Content: userQuery},
		},
	})
	if err != nil {
		return err
	}
	sanitized := sanitizeBeforeStorage(resp.Choices[0].Message.Content)
	return vectorStore.AddDocuments(ctx, []Document{{ID: sessionID, Content: sanitized}})
}
