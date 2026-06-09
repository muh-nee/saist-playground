package main

import (
	"html/template"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

func renderSummary(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()
	topic := r.URL.Query().Get("topic")
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: topic},
		},
	})
	if err != nil {
		http.Error(w, "error", http.StatusInternalServerError)
		return
	}
	output := resp.Choices[0].Message.Content
	t, _ := template.New("page").Parse("<html><body><p>{{.}}</p></body></html>")
	t.Execute(w, output)
}
