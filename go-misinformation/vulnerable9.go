package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/llms/anthropic"
)

func main() {
	llm, _ := anthropic.New(anthropic.WithModel("claude-opus-4-5"))
	r := gin.Default()

	r.POST("/advice", func(c *gin.Context) {
		var body struct {
			Question string `json:"question"`
		}
		c.ShouldBindJSON(&body)

		answer, _ := llms.GenerateFromSinglePrompt(
			context.Background(),
			llm,
			body.Question,
			llms.WithMaxTokens(500),
		)
		c.JSON(http.StatusOK, gin.H{"advice": answer})
	})

	r.Run(":8080")
}
