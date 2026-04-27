package main

import (
	"context"
	"fmt"
	"net/http"

	"github.com/anthropics/anthropic-sdk-go"
)

var anthropicClient = anthropic.NewClient()

func handlePersonaChat(w http.ResponseWriter, r *http.Request) {
	persona := r.URL.Query().Get("persona")
	message := r.FormValue("message")

	systemPrompt := fmt.Sprintf("Act as: %s", persona)

	msg, err := anthropicClient.Messages.New(context.Background(), anthropic.MessageNewParams{
		Model:     anthropic.ModelClaude3_5SonnetLatest,
		MaxTokens: 1024,
		System: []anthropic.TextBlockParam{
			{Text: systemPrompt},
		},
		Messages: []anthropic.MessageParam{
			anthropic.NewUserTextBlock(message),
		},
	})
	if err != nil {
		http.Error(w, "error", http.StatusInternalServerError)
		return
	}
	fmt.Fprintln(w, msg.Content[0].Text)
}

func main() {
	http.HandleFunc("/persona", handlePersonaChat)
	http.ListenAndServe(":8080", nil)
}
