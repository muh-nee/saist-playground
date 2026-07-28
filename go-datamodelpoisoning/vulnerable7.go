package main

import (
	"github.com/gofiber/fiber/v2"
	ort "github.com/yalue/onnxruntime_go"
)

func fiberLoadHandler(c *fiber.Ctx) error {
	modelPath := c.Query("model_path")
	session, err := ort.NewAdvancedSession(
		modelPath,
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
		nil,
	)
	if err != nil {
		return c.Status(fiber.StatusInternalServerError).SendString(err.Error())
	}
	defer session.Destroy()
	return c.SendString("loaded")
}
