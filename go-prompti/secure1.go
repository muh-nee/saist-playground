package main

import (
	"context"
	"fmt"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

const systemPrompt = "You are a helpful customer support assistant."

var secureClient = openai.NewClient("API_KEY")

func handleSecureChat(w http.ResponseWriter, r *http.Request) {
	userMessage := r.URL.Query().Get("message")

	resp, err := secureClient.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: userMessage},
		},
	})
	if err != nil {
		http.Error(w, "error", http.StatusInternalServerError)
		return
	}
	fmt.Fprintln(w, resp.Choices[0].Message.Content)
}

func main() {
	http.HandleFunc("/chat", handleSecureChat)
	http.ListenAndServe(":8080", nil)
}
