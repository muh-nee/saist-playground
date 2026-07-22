package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "Internal assistant. Confidential business logic."

func chatHandler(c *gin.Context) {
	var body struct {
		Message string `json:"message"`
	}
	c.ShouldBindJSON(&body)
	resp, err := client.CreateChatCompletion(context.Background(), openai.ChatCompletionRequest{
		Model:     openai.GPT4o,
		MaxTokens: 1024,
		Messages: []openai.ChatCompletionMessage{
			{Role: openai.ChatMessageRoleSystem, Content: systemPrompt},
			{Role: openai.ChatMessageRoleUser, Content: body.Message},
		},
	})
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "llm error"})
		return
	}
	c.JSON(http.StatusOK, gin.H{"answer": resp.Choices[0].Message.Content, "disclaimer": "AI-generated content. Verify independently."})
}

func adminPromptHandler(c *gin.Context) {
	if c.GetHeader("X-Admin-Token") != "admin-secret" {
		c.JSON(http.StatusForbidden, gin.H{"error": "forbidden"})
		return
	}
	c.JSON(http.StatusOK, gin.H{"prompt_length": len(systemPrompt), "configured": true})
}
