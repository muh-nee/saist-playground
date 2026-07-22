package main

import (
	"context"
	"encoding/json"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var llmClient *openai.Client

const systemPrompt = "You are an internal assistant with access to customer pricing and contracts."

type DebugResponse struct {
	SystemPrompt string `json:"system_prompt"`
	Model        string `json:"model"`
}

func chatHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Message string `json:"message"`
	}
	json.NewDecoder(r.Body).Decode(&body)
	resp, err := llmClient.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
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

func debugHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(DebugResponse{
		SystemPrompt: systemPrompt,
		Model:        openai.GPT4o,
	})
}
