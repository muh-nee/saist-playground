package main

import (
	"context"
	"encoding/json"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "Internal assistant. Has access to internal pricing data."

type ChatResponse struct {
	Answer     string `json:"answer"`
	TokensUsed int    `json:"tokens_used"`
	Disclaimer string `json:"disclaimer"`
}

func chatHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Message string `json:"message"`
	}
	json.NewDecoder(r.Body).Decode(&body)

	resp, err := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model:     openai.GPT4o,
		MaxTokens: 1024,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: body.Message},
		},
	})
	if err != nil {
		http.Error(w, "internal error", http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(ChatResponse{
		Answer:     resp.Choices[0].Message.Content,
		TokensUsed: resp.Usage.TotalTokens,
		Disclaimer: "AI-generated content. Verify independently.",
	})
}
