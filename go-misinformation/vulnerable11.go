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

	e.POST("/financial-advice", func(c echo.Context) error {
		var body struct {
			Question string `json:"question"`
		}
		c.Bind(&body)

		answer, _ := llms.GenerateFromSinglePrompt(
			context.Background(),
			llm,
			body.Question,
			llms.WithMaxTokens(500),
		)
		return c.JSON(http.StatusOK, map[string]string{"advice": answer})
	})

	e.Start(":8080")
}
