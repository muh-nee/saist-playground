package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func runProductionEval(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	sample := r.FormValue("sample")
	response, _ := client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: "Choose the production account to suspend."},
			{Role: openai.ChatMessageRoleUser, Content: sample},
		},
	})
	suspendProductionAccount(response.Choices[0].Message.Content)
}

func suspendProductionAccount(string) {}
