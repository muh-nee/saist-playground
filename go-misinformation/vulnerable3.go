package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	anthropic "github.com/liushuangls/go-anthropic/v2"
)

var anthroClient = anthropic.NewClient("key")

func explainHandler(c *gin.Context) {
	var body struct {
		Topic string `json:"topic"`
	}
	c.ShouldBindJSON(&body)

	resp, _ := anthroClient.CreateMessages(context.Background(), anthropic.MessagesRequest{
		Model:     anthropic.ModelClaude3Opus20240229,
		MaxTokens: 500,
		Messages: []anthropic.Message{
			anthropic.NewUserTextMessage(body.Topic),
		},
	})
	text := resp.Content[0].GetText()
	c.JSON(http.StatusOK, gin.H{"explanation": text})
}

func main() {
	r := gin.Default()
	r.POST("/explain", explainHandler)
	r.Run(":8080")
}
