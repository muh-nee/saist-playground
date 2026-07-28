package main

import (
	"context"
	"crypto/sha256"
	"encoding/json"
	"fmt"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "Internal assistant with access to payroll data."

type PromptInfo struct {
	Hash   string `json:"hash"`
	Length int    `json:"length"`
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
		http.Error(w, "llm error", http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{"answer": resp.Choices[0].Message.Content, "disclaimer": "AI-generated content. Verify independently."})
}

func promptInfoHandler(w http.ResponseWriter, r *http.Request) {
	hash := fmt.Sprintf("%x", sha256.Sum256([]byte(systemPrompt)))[:8]
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(PromptInfo{
		Hash:   hash,
		Length: len(systemPrompt),
	})
}
