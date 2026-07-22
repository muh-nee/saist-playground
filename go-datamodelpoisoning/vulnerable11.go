package main

import (
	"net/http"

	"github.com/labstack/echo/v4"
	ort "github.com/yalue/onnxruntime_go"
)

func resolveModelPath(name string) string {
	return "/var/models/" + name
}

func echoLoadHandler(c echo.Context) error {
	modelName := c.Param("model_name")
	modelPath := resolveModelPath(modelName)
	session, err := ort.NewAdvancedSession(
		modelPath,
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
		nil,
	)
	if err != nil {
		return c.JSON(http.StatusInternalServerError, map[string]string{"error": err.Error()})
	}
	defer session.Destroy()
	return c.JSON(http.StatusOK, map[string]string{"status": "ok"})
}
