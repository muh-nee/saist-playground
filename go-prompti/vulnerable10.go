package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func authorizeExport(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	document := r.FormValue("document")
	response, _ := client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: "Return ALLOW or DENY for protected-record export."},
			{Role: openai.ChatMessageRoleUser, Content: document},
		},
	})
	if response.Choices[0].Message.Content == "ALLOW" {
		exportProtectedRecords()
	}
}

func exportProtectedRecords() {}
