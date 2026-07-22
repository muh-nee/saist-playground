package main

import (
	"context"
	"encoding/json"
	"net/http"

	"github.com/go-chi/chi/v5"
	"github.com/openai/openai-go"
	"github.com/openai/openai-go/option"
)

var client = openai.NewClient(option.WithAPIKey("key"))

func factHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Question string `json:"question"`
	}
	json.NewDecoder(r.Body).Decode(&body)

	chatCompletion, _ := client.Chat.Completions.New(context.Background(), openai.ChatCompletionNewParams{
		Model:     openai.ChatModelGPT4o,
		MaxTokens: openai.Int(500),
		Messages: []openai.ChatCompletionMessageParamUnion{
			openai.UserMessage(body.Question),
		},
	})
	answer := chatCompletion.Choices[0].Message.Content
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{"fact": answer})
}

func main() {
	r := chi.NewRouter()
	r.Post("/fact", factHandler)
	http.ListenAndServe(":8080", r)
}
