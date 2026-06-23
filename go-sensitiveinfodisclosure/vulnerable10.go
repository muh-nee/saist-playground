package main

import (
	"context"
	"encoding/json"
	"fmt"
	"os"

	"github.com/sashabaranov/go-openai"
)

type DBConfig struct {
	DBPassword  string `json:"db_password"`
	Environment string `json:"environment"`
}

var client = openai.NewClient(os.Getenv("OPENAI_API_KEY"))

func troubleshootDB(ctx context.Context, cfg DBConfig, errMsg string) (string, error) {
	resp, err := client.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: "user", Content: fmt.Sprintf("Troubleshoot DB error in %s: %s. Password: %s", cfg.Environment, errMsg, cfg.DBPassword)},
		},
	})
	if err != nil {
		return "", err
	}
	return resp.Choices[0].Message.Content, nil
}

func main() {
	configBytes, _ := os.ReadFile("config.json")
	var cfg DBConfig
	json.Unmarshal(configBytes, &cfg)
	result, _ := troubleshootDB(context.Background(), cfg, "connection timeout")
	fmt.Println(result)
}
