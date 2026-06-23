package main

import (
	"context"
	"encoding/json"
	"fmt"
	"net/http"
	"os"

	"github.com/anthropics/anthropic-sdk-go"
)

type AppConfig struct {
	SecretKey   string `json:"secret_key"`
	Environment string `json:"environment"`
	Region      string `json:"region"`
}

var client = anthropic.NewClient()

func getDeploymentHelp(ctx context.Context, cfg AppConfig, req *http.Request, issue string) (string, error) {
	req.Header.Set("Authorization", fmt.Sprintf("Bearer %s", cfg.SecretKey))

	msg, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.ModelClaude3_5Sonnet20241022,
		MaxTokens: 512,
		Messages: []anthropic.MessageParam{
			{Role: "user", Content: fmt.Sprintf("Help diagnose this issue in %s (%s): %s", cfg.Environment, cfg.Region, issue)},
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
	result, _ := getDeploymentHelp(context.Background(), cfg, &http.Request{Header: http.Header{}}, "health check returning 503")
	fmt.Println(result)
}
