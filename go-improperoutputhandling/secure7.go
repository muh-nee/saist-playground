package main

import (
	"context"
	"fmt"
	"net/http"
	"net/url"
	"strings"

	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

var allowedHosts = map[string]bool{
	"api.example.com":  true,
	"docs.example.com": true,
}

func fetchResourceSafe(ctx context.Context, description string) (*http.Response, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4oMini,
		Messages: []openai.ChatCompletionMessage{
			{Role: "system", Content: "Return only the URL for the resource described."},
			{Role: "user", Content: description},
		},
	})
	if err != nil {
		return nil, err
	}
	rawURL := strings.TrimSpace(resp.Choices[0].Message.Content)
	u, err := url.Parse(rawURL)
	if err != nil || (u.Scheme != "http" && u.Scheme != "https") {
		return nil, fmt.Errorf("invalid url")
	}
	if !allowedHosts[u.Host] {
		return nil, fmt.Errorf("host not allowed: %s", u.Host)
	}
	return http.Get(u.String())
}
