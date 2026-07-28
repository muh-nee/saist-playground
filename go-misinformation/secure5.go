package main

import (
	"context"
	"encoding/json"
	"log"
	"net/http"

	"github.com/openai/openai-go"
	"github.com/openai/openai-go/option"
)

var client = openai.NewClient(option.WithAPIKey("key"))

func classifyHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Text string `json:"text"`
	}
	json.NewDecoder(r.Body).Decode(&body)

	chatCompletion, _ := client.Chat.Completions.New(context.Background(), openai.ChatCompletionNewParams{
		Model:     openai.ChatModelGPT4o,
		MaxTokens: openai.Int(10),
		Messages: []openai.ChatCompletionMessageParamUnion{
			openai.UserMessage("Reply with one word: POSITIVE, NEGATIVE, or NEUTRAL.\n\n" + body.Text),
		},
	})
	label := chatCompletion.Choices[0].Message.Content
	log.Printf("classified text as %s", label)
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{"label": label})
}

func main() {
	http.HandleFunc("/classify", classifyHandler)
	http.ListenAndServe(":8080", nil)
}
