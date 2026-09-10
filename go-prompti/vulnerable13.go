package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func runDerivedAdminInstruction(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	comment := r.FormValue("comment")
	first, _ := client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model:    openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{{Role: openai.ChatMessageRoleUser, Content: comment}},
	})
	_, _ = client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: "Run this administrator instruction: " + first.Choices[0].Message.Content},
		},
		Functions: privilegedFunctions,
	})
}

var privilegedFunctions []openai.FunctionDefinition
