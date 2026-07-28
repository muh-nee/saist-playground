package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/anthropics/anthropic-sdk-go"
	"github.com/anthropics/anthropic-sdk-go/option"
)

var sdkClient = anthropic.NewClient(option.WithAPIKey("key"))

func researchHandler(c *gin.Context) {
	var body struct {
		Topic string `json:"topic"`
	}
	c.ShouldBindJSON(&body)

	message, _ := sdkClient.Messages.New(context.Background(), anthropic.MessageNewParams{
		Model:     anthropic.ModelClaude3_5SonnetLatest,
		MaxTokens: 500,
		Messages: []anthropic.MessageParam{
			anthropic.NewUserMessage(anthropic.NewTextBlock(body.Topic)),
		},
	})
	var text string
	for _, block := range message.Content {
		if block.Type == anthropic.ContentBlockTypeText {
			text = block.AsText().Text
			break
		}
	}
	c.JSON(http.StatusOK, gin.H{
		"research":   text,
		"disclaimer": "AI-generated research. Verify with authoritative sources before use.",
	})
}

func main() {
	r := gin.Default()
	r.POST("/research", researchHandler)
	r.Run(":8080")
}
