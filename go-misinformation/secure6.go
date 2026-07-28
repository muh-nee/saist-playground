package main

import (
	"context"
	"log"

	anthropic "github.com/liushuangls/go-anthropic/v2"
)

var client = anthropic.NewClient("key")

func enrichDocument(docText string) map[string]string {
	resp, _ := client.CreateMessages(context.Background(), anthropic.MessagesRequest{
		Model:     anthropic.ModelClaude3Opus20240229,
		MaxTokens: 500,
		Messages: []anthropic.Message{
			anthropic.NewUserTextMessage("Extract key entities from:\n\n" + docText),
		},
	})
	entities := resp.Content[0].GetText()
	log.Printf("entities extracted for document")
	return map[string]string{"entities": entities, "original": docText}
}
