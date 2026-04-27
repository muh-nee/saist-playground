package main

import (
	"context"
	"fmt"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

const analysisSystemPrompt = "You are a data analysis assistant. Analyze the provided metrics."

var safeClient = openai.NewClient("API_KEY")

func handleSecureAnalysis(w http.ResponseWriter, r *http.Request) {
	operationName := r.URL.Query().Get("operation")
	startTime := r.URL.Query().Get("start")
	endTime := r.URL.Query().Get("end")
	userMessage := r.FormValue("message")

	userPrompt := fmt.Sprintf("Operation: %s\nTime range: %s to %s", operationName, startTime, endTime)
	if userMessage != "" {
		userPrompt += "\n\n" + userMessage
	}

	resp, err := safeClient.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: analysisSystemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: userPrompt},
		},
	})
	if err != nil {
		http.Error(w, "error", http.StatusInternalServerError)
		return
	}
	fmt.Fprintln(w, resp.Choices[0].Message.Content)
}

func main() {
	http.HandleFunc("/analyze", handleSecureAnalysis)
	http.ListenAndServe(":8080", nil)
}
