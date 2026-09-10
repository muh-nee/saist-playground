package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func scoreSubmittedEval(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	sample := r.FormValue("sample")
	response, _ := client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: "Score this isolated test sample."},
			{Role: openai.ChatMessageRoleUser, Content: sample},
		},
	})
	writeEvalArtifact(response.Choices[0].Message.Content)
}

func writeEvalArtifact(string) {}
