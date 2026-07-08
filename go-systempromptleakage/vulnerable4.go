package main

import (
	"context"
	"encoding/json"
	"fmt"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "You are a financial analysis assistant. Internal: use ACME Corp margin data."

func chatHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Query string `json:"query"`
	}
	json.NewDecoder(r.Body).Decode(&body)
	resp, err := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: body.Query},
		},
	})
	if err != nil {
		http.Error(w, "llm error", http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{"result": resp.Choices[0].Message.Content})
}

func promptHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "%s", systemPrompt)
}
