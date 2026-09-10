package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func formatAuditTitle(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	comment := r.FormValue("comment")
	first, _ := client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model:    openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{{Role: openai.ChatMessageRoleUser, Content: comment}},
	})
	formatted, _ := client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: "Format the text as a short audit title."},
			{Role: openai.ChatMessageRoleUser, Content: first.Choices[0].Message.Content},
		},
	})
	writeAuditTitle(formatted.Choices[0].Message.Content)
}

func writeAuditTitle(string) {}
