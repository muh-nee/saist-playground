package main

import (
	"context"
	"encoding/json"
	"net/http"

	anthropic "github.com/liushuangls/go-anthropic/v2"
)

var client = anthropic.NewClient("key")

func explainHandler(w http.ResponseWriter, r *http.Request) {
	var body struct {
		Topic string `json:"topic"`
	}
	json.NewDecoder(r.Body).Decode(&body)

	resp, _ := client.CreateMessages(context.Background(), anthropic.MessagesRequest{
		Model:     anthropic.ModelClaude3Opus20240229,
		MaxTokens: 500,
		Messages: []anthropic.Message{
			anthropic.NewUserTextMessage(body.Topic),
		},
	})
	text := resp.Content[0].GetText()
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(map[string]string{
		"explanation": text,
		"disclaimer":  "AI-generated. Always verify with a qualified professional.",
	})
}

func main() {
	http.HandleFunc("/explain", explainHandler)
	http.ListenAndServe(":8080", nil)
}
