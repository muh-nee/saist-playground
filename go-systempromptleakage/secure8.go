package main

import (
	"context"
	"encoding/json"
	"fmt"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "You are a data analysis assistant. Summarize the provided data."

type AnalysisResponse struct {
	Analysis   string `json:"analysis"`
	Disclaimer string `json:"disclaimer"`
}

func analyzeHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Data string `json:"data"`
	}
	json.NewDecoder(r.Body).Decode(&body)

	resp, err := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model:     openai.GPT4o,
		MaxTokens: 1024,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: fmt.Sprintf("Analyze this data: %s", body.Data)},
		},
	})
	if err != nil {
		http.Error(w, "internal error", http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(AnalysisResponse{Analysis: resp.Choices[0].Message.Content, Disclaimer: "AI-generated content. Verify independently."})
}
