package main

import (
	"context"
	"net/http"

	"github.com/labstack/echo/v4"
	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "You are a code review assistant. Internal: flag all uses of deprecated APIs."

type ConfigResponse struct {
	SystemPrompt string `json:"system_prompt"`
	Model        string `json:"model"`
}

func chatHandler(c echo.Context) error {
	var body struct {
		Message string `json:"message"`
	}
	c.Bind(&body)
	resp, err := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: body.Message},
		},
	})
	if err != nil {
		return c.JSON(http.StatusInternalServerError, map[string]string{"error": "llm error"})
	}
	return c.JSON(http.StatusOK, map[string]string{"answer": resp.Choices[0].Message.Content})
}

func configHandler(c echo.Context) error {
	return c.JSON(http.StatusOK, ConfigResponse{
		SystemPrompt: systemPrompt,
		Model:        openai.GPT4o,
	})
}
