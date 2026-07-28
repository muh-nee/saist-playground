package main

import (
	"context"
	"encoding/json"
	"net/http"

	anthropic "github.com/liushuangls/go-anthropic/v2"
)

var client = anthropic.NewClient("key")

func legalHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Question string `json:"question"`
	}
	json.NewDecoder(r.Body).Decode(&body)

	resp, _ := client.CreateMessages(context.Background(), anthropic.MessagesRequest{
		Model:     anthropic.ModelClaude3Opus20240229,
		MaxTokens: 500,
		Messages: []anthropic.Message{
			anthropic.NewUserTextMessage(body.Question),
		},
	})
	text := resp.Content[0].GetText()
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{"legal_advice": text})
}

func main() {
	http.HandleFunc("/legal", legalHandler)
	http.ListenAndServe(":8080", nil)
}
