package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	gogpt "github.com/sashabaranov/go-openai"
)

var gptClient = gogpt.NewClient("key")

func answerHandler(c *gin.Context) {
	var body struct {
		Question string `json:"question"`
	}
	c.ShouldBindJSON(&body)

	resp, _ := gptClient.CreateChatCompletion(context.Background(), gogpt.ChatCompletionRequest{
		Model:     gogpt.GPT4o,
		MaxTokens: 500,
		Messages: []gogpt.ChatCompletionMessage{
			{Role: gogpt.ChatMessageRoleUser, Content: body.Question},
		},
	})
	answer := resp.Choices[0].Message.Content
	c.JSON(http.StatusOK, gin.H{"answer": answer})
}

func main() {
	r := gin.Default()
	r.POST("/answer", answerHandler)
	r.Run(":8080")
}
