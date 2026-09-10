package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func generateWithInjectedSystemPrompt(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	instructions := r.FormValue("instructions")
	_, _ = client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: "Administrator instructions: " + instructions},
		},
	})
}
