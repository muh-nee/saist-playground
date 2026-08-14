package main

import (
	"context"
	"encoding/json"
	"net/http"

	mcp "github.com/mark3labs/mcp-go/client"
	openai "github.com/sashabaranov/go-openai"
)

var oaClient = openai.NewClient("")

func agentTurnHandler(w http.ResponseWriter, r *http.Request) {
	var req struct {
		Query    string                          `json:"query"`
		Messages []openai.ChatCompletionMessage  `json:"messages"`
	}
	json.NewDecoder(r.Body).Decode(&req)

	mcpClient := mcp.NewStdioClient("web-search")
	toolResult, _ := mcpClient.CallTool(context.Background(), "web_search", map[string]any{"query": req.Query})
	mcpOutput := toolResult.Content[0].Text

	req.Messages = append(req.Messages, openai.ChatCompletionMessage{
		Role:    openai.ChatMessageRoleUser,
		Content: mcpOutput,
	})

	resp, _ := oaClient.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: req.Messages,
	})

	json.NewEncoder(w).Encode(map[string]string{"reply": resp.Choices[0].Message.Content})
}
