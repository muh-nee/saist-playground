package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "You are a product recommendation engine. Internal pricing: A=$100, B=$50, C=$20."

func chatHandler(c *gin.Context) {
	var body struct {
		Message string `json:"message"`
	}
	c.ShouldBindJSON(&body)
	resp, err := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model: openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: body.Message},
		},
	})
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "llm error"})
		return
	}
	c.JSON(http.StatusOK, gin.H{"answer": resp.Choices[0].Message.Content})
}

func debugPromptHandler(c *gin.Context) {
	c.String(http.StatusOK, systemPrompt)
}
