package main

import (
	"context"
	"encoding/json"
	"fmt"
	"os"

	"github.com/anthropics/anthropic-sdk-go"
)

type AppConfig struct {
	StripeSecretKey string `json:"stripe_secret_key"`
	Environment     string `json:"environment"`
}

var client = anthropic.NewClient()

func runAdminAssistant(ctx context.Context, cfg AppConfig, query string) (string, error) {
	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaude3_5Sonnet20241022,
		MaxTokens: 1024,
		System:    fmt.Sprintf("You are an admin assistant. For payment lookups use Stripe key %s.", cfg.StripeSecretKey),
		Messages: []anthropic.MessageParam{
			{Role: "user", Content: query},
		},
	})
	if err != nil {
		return "", err
	}
	return msg.Content[0].Text, nil
}

func main() {
	configBytes, _ := os.ReadFile("config.json")
	var cfg AppConfig
	json.Unmarshal(configBytes, &cfg)
	result, _ := runAdminAssistant(context.Background(), cfg, "Check latest payment status")
	fmt.Println(result)
}
