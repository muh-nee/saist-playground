package main

import (
	"context"
	"net/http"

	"github.com/labstack/echo/v4"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/llms/openai"
)

func main() {
	llm, _ := openai.New(openai.WithModel("gpt-4o"))
	e := echo.New()

	e.POST("/summarize", func(c echo.Context) error {
		var body struct {
			Text string `json:"text"`
		}
		c.Bind(&body)

		answer, _ := llms.GenerateFromSinglePrompt(
			context.Background(),
			llm,
			"Summarize: "+body.Text,
			llms.WithMaxTokens(500),
		)
		return c.JSON(http.StatusOK, map[string]string{"summary": answer})
	})

	e.Start(":8080")
}
