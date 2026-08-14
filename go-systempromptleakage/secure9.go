package main

import (
	"encoding/json"
	"net/http"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client = openai.NewClient("sk-xxx")

var tools = []openai.Tool{{
	Type: openai.ToolTypeFunction,
	Function: &openai.FunctionDefinition{Name: "get_data", Description: "Internal lookup."},
}}

type retriever struct{}

func (r *retriever) GetRelevantDocuments(query string) []string {
	return []string{"Policy: refunds require approval."}
}

var ret = &retriever{}

func chatHandler(w http.ResponseWriter, r *http.Request) {
	docs := ret.GetRelevantDocuments(r.FormValue("message"))
	policyText := strings.Join(docs, "\n")
	resp, _ := client.CreateChatCompletion(r.Context(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: policyText},
			{Role: openai.ChatMessageRoleUser, Content: r.FormValue("message")},
		},
		Tools: tools,
	})
	json.NewEncoder(w).Encode(map[string]string{"reply": resp.Choices[0].Message.Content})
}

func main() {
	http.HandleFunc("/chat", chatHandler)
	http.ListenAndServe(":8080", nil)
}
