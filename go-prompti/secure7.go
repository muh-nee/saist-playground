package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func summarizeDocument(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	document := r.FormValue("document")
	_, _ = client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: "Summarize the submitted document."},
			{Role: openai.ChatMessageRoleUser, Content: document},
		},
	})
}
