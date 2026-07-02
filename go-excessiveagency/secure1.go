package main

import (
	"context"
	"os"

	openai "github.com/sashabaranov/go-openai"
)

var secureClient *openai.Client

var reportFiles = map[string]string{
	"q1_sales": "/var/app/reports/q1_sales.csv",
	"q2_sales": "/var/app/reports/q2_sales.csv",
	"q3_sales": "/var/app/reports/q3_sales.csv",
	"q4_sales": "/var/app/reports/q4_sales.csv",
}

func readReport(reportName string) (string, error) {
	path := reportFiles[reportName]
	data, err := os.ReadFile(path)
	return string(data), err
}

func handleReportRequest(ctx context.Context, userQuery string) (string, error) {
	_, err := secureClient.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: "user", Content: userQuery}},
		Tools: []openai.Tool{
			{
				Type: openai.ToolTypeFunction,
				Function: &openai.FunctionDefinition{
					Name:        "read_report",
					Description: "Read a quarterly sales report",
					Parameters: map[string]interface{}{
						"type": "object",
						"properties": map[string]interface{}{
							"report_name": map[string]interface{}{
								"type": "string",
								"enum": []string{"q1_sales", "q2_sales", "q3_sales", "q4_sales"},
							},
						},
						"required": []string{"report_name"},
					},
				},
			},
		},
	})
	return "", err
}
