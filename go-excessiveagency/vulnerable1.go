package main

import (
	"context"
	"net/smtp"

	openai "github.com/sashabaranov/go-openai"
)

var mailClient *openai.Client

func sendEmail(to, subject, body string) error {
	msg := []byte("To: " + to + "\r\nSubject: " + subject + "\r\n\r\n" + body)
	return smtp.SendMail("smtp.example.com:25", nil, "noreply@example.com", []string{to}, msg)
}

func handleEmailRequest(ctx context.Context, userQuery string) (string, error) {
	_, err := mailClient.CreateChatCompletion(ctx, openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: "user", Content: userQuery}},
		Tools: []openai.Tool{
			{
				Type: openai.ToolTypeFunction,
				Function: &openai.FunctionDefinition{
					Name:        "send_email",
					Description: "Send an email to any recipient",
					Parameters: map[string]interface{}{
						"type": "object",
						"properties": map[string]interface{}{
							"to":      map[string]interface{}{"type": "string"},
							"subject": map[string]interface{}{"type": "string"},
							"body":    map[string]interface{}{"type": "string"},
						},
						"required": []string{"to", "subject", "body"},
					},
				},
			},
		},
	})
	return "", err
}
