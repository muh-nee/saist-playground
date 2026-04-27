package main

import (
	"context"
	"fmt"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var aiClient = openai.NewClient("API_KEY")

func handleAnalysis(w http.ResponseWriter, r *http.Request) {
	targetLanguage := r.URL.Query().Get("lang")
	tone := r.URL.Query().Get("tone")
	text := r.FormValue("text")

	systemPrompt := fmt.Sprintf(
		"You are a translation assistant. Translate to %s using a %s tone.",
		targetLanguage,
		tone,
	)

	resp, err := aiClient.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: text},
		},
	})
	if err != nil {
		http.Error(w, "error", http.StatusInternalServerError)
		return
	}
	fmt.Fprintln(w, resp.Choices[0].Message.Content)
}

func main() {
	http.HandleFunc("/analyze", handleAnalysis)
	http.ListenAndServe(":8080", nil)
}
