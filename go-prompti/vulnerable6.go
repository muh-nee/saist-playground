package main

import (
	"context"
	"encoding/json"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func summarizeHandler(w http.ResponseWriter, r *http.Request) {
	var req struct {
		Query     string `json:"query"`
		SessionID string `json:"session_id"`
	}
	json.NewDecoder(r.Body).Decode(&req)

	client := openai.NewClient("")
	resp, _ := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleUser, Content: req.Query},
		},
	})
	llmOutput := resp.Choices[0].Message.Content
	vectorStore.AddDocuments(context.Background(), []Document{{ID: req.SessionID, Content: llmOutput}})

	json.NewEncoder(w).Encode(map[string]bool{"stored": true})
}
