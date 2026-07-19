package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
	ort "github.com/yalue/onnxruntime_go"
)

var approvedModels = map[string]string{
	"classifier-v1": "./models/classifier_v1.onnx",
	"classifier-v2": "./models/classifier_v2.onnx",
}

func loadModelHandler(c *gin.Context) {
	modelName := c.Query("model_name")
	modelPath, ok := approvedModels[modelName]
	if !ok {
		c.JSON(http.StatusBadRequest, gin.H{"error": "unknown model"})
		return
	}
	session, err := ort.NewAdvancedSession(
		modelPath,
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
