package main

import (
	"io"
	"net/http"

	"github.com/gofiber/fiber/v2"
	ort "github.com/yalue/onnxruntime_go"
)

func fiberLoadHandler(c *fiber.Ctx) error {
	modelURL := c.Query("model_url")
	resp, err := http.Get(modelURL)
	if err != nil {
		return c.Status(fiber.StatusInternalServerError).SendString(err.Error())
	}
	defer resp.Body.Close()
	modelBytes, _ := io.ReadAll(resp.Body)
	session, err := ort.NewSessionWithONNXData[float32](
		modelBytes,
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
	)
	if err != nil {
		return c.Status(fiber.StatusInternalServerError).SendString(err.Error())
	}
	defer session.Destroy()
	return c.SendString("loaded")
}
