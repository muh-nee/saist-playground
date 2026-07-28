package main

import (
	"context"
	"encoding/json"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "You are a code review assistant. Identify bugs and suggest improvements."

type ReviewResponse struct {
	Review     string `json:"review"`
	Disclaimer string `json:"disclaimer"`
}

func reviewHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Code string `json:"code"`
	}
	json.NewDecoder(r.Body).Decode(&body)

	resp, err := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model:     openai.GPT4o,
		MaxTokens: 1024,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: "Review this code:\n" + body.Code},
		},
	})
	if err != nil {
		http.Error(w, "internal error", http.StatusInternalServerError)
		return
	}
	llmOutput := resp.Choices[0].Message.Content
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(ReviewResponse{Review: llmOutput, Disclaimer: "AI-generated content. Verify independently."})
}
