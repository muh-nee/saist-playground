package main

import (
	"context"
	"database/sql"

	openai "github.com/sashabaranov/go-openai"
)

var analyticsDB *sql.DB
var analyticsClient *openai.Client

func getMetric(metricName string) (float64, error) {
	var value float64
	err := analyticsDB.QueryRowContext(context.Background(),
		"SELECT value FROM metrics WHERE name = ? AND recorded_at > NOW() - INTERVAL 1 DAY",
		metricName,
	).Scan(&value)
	return value, err
}

func handleMetricRequest(ctx context.Context, userQuery string) (string, error) {
	_, err := analyticsClient.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: "user", Content: userQuery}},
		Tools: []openai.Tool{
			{
				Type: openai.ToolTypeFunction,
				Function: &openai.FunctionDefinition{
					Name:        "get_metric",
					Description: "Get a specific analytics metric value",
					Parameters: map[string]interface{}{
						"type": "object",
						"properties": map[string]interface{}{
							"metric_name": map[string]interface{}{
								"type": "string",
								"enum": []string{"page_views", "active_users", "conversion_rate", "revenue"},
							},
						},
						"required": []string{"metric_name"},
					},
				},
			},
		},
	})
	return "", err
}
