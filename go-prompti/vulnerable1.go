package main

import (
	"context"
	"fmt"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var openaiClient = openai.NewClient("API_KEY")

func handleChat(w http.ResponseWriter, r *http.Request) {
	userRole := r.URL.Query().Get("role")
	userMessage := r.URL.Query().Get("message")

	resp, err := openaiClient.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{
				Role:    openai.ChatMessageRoleSystem,
				Content: "You are a " + userRole + " assistant.",
			},
			{
				Role:    openai.ChatMessageRoleUser,
				Content: userMessage,
			},
		},
	})
	if err != nil {
		http.Error(w, "error", http.StatusInternalServerError)
		return
	}
	fmt.Fprintln(w, resp.Choices[0].Message.Content)
}

func main() {
	http.HandleFunc("/chat", handleChat)
	http.ListenAndServe(":8080", nil)
}
