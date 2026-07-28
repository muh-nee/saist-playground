package main

import (
	"context"
	"encoding/json"
	"net/http"

	"github.com/openai/openai-go"
	"github.com/openai/openai-go/option"
)

var client = openai.NewClient(option.WithAPIKey("key"))

const disclaimer = "AI-generated content. Verify independently before acting on this information."

func askHandler(w http.ResponseWriter, r *http.Request) {
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
	json.NewEncoder(w).Encode(map[string]string{"answer": answer, "disclaimer": disclaimer})
}

func main() {
	http.HandleFunc("/ask", askHandler)
	http.ListenAndServe(":8080", nil)
}
