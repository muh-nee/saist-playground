package main

import (
	"context"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"net/url"

	openai "github.com/sashabaranov/go-openai"
)

var quoteClient *openai.Client

func lookupSymbol(symbol string) (map[string]interface{}, error) {
	resp, err := http.Get("https://api.example.com/quote?s=" + url.QueryEscape(symbol))
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	body, _ := io.ReadAll(resp.Body)
	var result map[string]interface{}
	json.Unmarshal(body, &result)
	return result, nil
}

func handleQuoteRequest(ctx context.Context, userQuery string) (string, error) {
	_, err := quoteClient.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: "user", Content: userQuery}},
		Tools: []openai.Tool{
			{
				Type: openai.ToolTypeFunction,
				Function: &openai.FunctionDefinition{
					Name:        "lookup_symbol",
					Description: "Look up a stock quote by ticker symbol",
					Parameters: map[string]interface{}{
						"type": "object",
						"properties": map[string]interface{}{
							"symbol": map[string]interface{}{"type": "string"},
						},
						"required": []string{"symbol"},
					},
				},
			},
		},
	})
	return fmt.Sprintf(""), err
}
