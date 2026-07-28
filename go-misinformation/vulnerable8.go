package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/openai/openai-go"
	"github.com/openai/openai-go/option"
)

var client = openai.NewClient(option.WithAPIKey("key"))

func medicalHandler(c *gin.Context) {
	var body struct {
		Condition string `json:"condition"`
	}
	c.ShouldBindJSON(&body)

	chatCompletion, _ := client.Chat.Completions.New(context.Background(), openai.ChatCompletionNewParams{
		Model:     openai.ChatModelGPT4o,
		MaxTokens: openai.Int(500),
		Messages: []openai.ChatCompletionMessageParamUnion{
			openai.UserMessage("What should I know about " + body.Condition + "?"),
		},
	})
	info := chatCompletion.Choices[0].Message.Content
	c.JSON(http.StatusOK, gin.H{"information": info})
}

func main() {
	r := gin.Default()
	r.POST("/medical-info", medicalHandler)
	r.Run(":8080")
}
