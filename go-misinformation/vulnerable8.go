package main

import (
	"context"
	"encoding/json"
	"net/http"

	"github.com/openai/openai-go"
	"github.com/openai/openai-go/option"
)

var client = openai.NewClient(option.WithAPIKey("key"))

func medicalHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Condition string `json:"condition"`
	}
	json.NewDecoder(r.Body).Decode(&body)

	chatCompletion, _ := client.Chat.Completions.New(context.Background(), openai.ChatCompletionNewParams{
		Model:     openai.ChatModelGPT4o,
		MaxTokens: openai.Int(500),
		Messages: []openai.ChatCompletionMessageParamUnion{
			openai.UserMessage("What should I know about " + body.Condition + "?"),
		},
	})
	info := chatCompletion.Choices[0].Message.Content
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{"information": info})
}

func main() {
	http.HandleFunc("/medical-info", medicalHandler)
	http.ListenAndServe(":8080", nil)
}
