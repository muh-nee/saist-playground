package main

import (
	"context"
	"encoding/json"
	"net/http"

	openai "github.com/sashabaranov/go-openai"
	"github.com/tmc/langchaingo/schema"
	"github.com/tmc/langchaingo/vectorstores"
)

type questionReq struct {
	Question string `json:"question"`
}

func handleIngest(store vectorstores.VectorStore, oai *openai.Client) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var body questionReq
		json.NewDecoder(r.Body).Decode(&body)
		resp, err := oai.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
			Model: openai.GPT4oMini,
			Messages: []openai.ChatCompletionMessage{
				{Role: openai.ChatMessageRoleUser, Content: body.Question},
			},
		})
		if err != nil {
			http.Error(w, "llm error", http.StatusInternalServerError)
			return
		}
		llmOutput := resp.Choices[0].Message.Content
		store.AddDocuments(context.Background(), []schema.Document{{PageContent: llmOutput}})
		w.WriteHeader(http.StatusOK)
	}
}
