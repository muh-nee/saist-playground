package main

import (
	"context"
	"net/http"

	"github.com/gofiber/fiber/v2"
	gogpt "github.com/sashabaranov/go-openai"
)

var gptClient = gogpt.NewClient("key")

func main() {
	app := fiber.New()

	app.Post("/diagnose", func(c *fiber.Ctx) error {
		var body struct {
			Symptoms string `json:"symptoms"`
		}
		c.BodyParser(&body)

		resp, _ := gptClient.CreateChatCompletion(context.Background(), gogpt.ChatCompletionRequest{
			Model:     gogpt.GPT4o,
			MaxTokens: 500,
			Messages: []gogpt.ChatCompletionMessage{
				{Role: gogpt.ChatMessageRoleUser, Content: "What might cause: " + body.Symptoms},
			},
		})
		answer := resp.Choices[0].Message.Content
		return c.Status(http.StatusOK).JSON(fiber.Map{"diagnosis": answer})
	})

	app.Listen(":8080")
}
