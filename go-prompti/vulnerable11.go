package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func invokeAdminAgent(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	message := r.FormValue("message")
	_, _ = client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: "Execute this administrator request: " + message},
		},
		Functions: adminFunctions,
	})
}

var adminFunctions []openai.FunctionDefinition
