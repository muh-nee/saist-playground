package main

import (
	"net/http"

	"github.com/labstack/echo/v4"
	torch "github.com/orktes/go-torch"
)

func loadTorchHandler(c echo.Context) error {
	modelPath := c.QueryParam("model_path")
	module, err := torch.LoadJITModule(modelPath)
	if err != nil {
		return c.JSON(http.StatusInternalServerError, map[string]string{"error": err.Error()})
	}
	defer module.Close()
	return c.JSON(http.StatusOK, map[string]string{"status": "loaded"})
}
