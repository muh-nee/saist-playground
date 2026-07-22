package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
	ort "github.com/yalue/onnxruntime_go"
)

type LoadRequest struct {
	ModelPath string `json:"model_path"`
}

func loadAdvancedHandler(c *gin.Context) {
	var req LoadRequest
	c.ShouldBindJSON(&req)
	session, err := ort.NewAdvancedSession(
		req.ModelPath,
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
		nil,
	)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	defer session.Destroy()
	c.JSON(http.StatusOK, gin.H{"status": "loaded"})
}
