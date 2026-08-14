package main

import (
	"encoding/json"
	"net/http"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client = openai.NewClient("sk-xxx")

type retriever struct{}

func (r *retriever) GetRelevantDocuments(query string) []string {
	return []string{"Internal policy: all refunds require manager approval."}
}

var ret = &retriever{}

func contextHandler(w http.ResponseWriter, r *http.Request) {
	docs := ret.GetRelevantDocuments(r.URL.Query().Get("q"))
	policyText := strings.Join(docs, "\n")
	client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: policyText},
			{Role: openai.ChatMessageRoleUser, Content: r.URL.Query().Get("q")},
		},
	})
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{"policy": policyText})
}

func main() {
	http.HandleFunc("/context", contextHandler)
	http.ListenAndServe(":8080", nil)
}
