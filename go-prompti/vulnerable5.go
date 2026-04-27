package main

import (
	"context"
	"fmt"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var client = openai.NewClient("API_KEY")

func handleDynamicSystem(w http.ResponseWriter, r *http.Request) {
	userName := r.FormValue("username")
	department := r.FormValue("department")
	userMessage := r.FormValue("message")

	systemPrompt := "You are an AI assistant."
	systemPrompt += " You are helping " + userName
	systemPrompt += " from the " + department + " department."

	resp, err := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
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
	http.HandleFunc("/help", handleDynamicSystem)
	http.ListenAndServe(":8080", nil)
}
