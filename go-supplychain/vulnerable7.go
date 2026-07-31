package main

import (
	"io"
	"net/http"

	"github.com/gin-gonic/gin"
	ort "github.com/yalue/onnxruntime_go"
)

type ModelRequest struct {
	URL string `json:"url"`
}

func ginLoadHandler(c *gin.Context) {
	var req ModelRequest
	c.ShouldBindJSON(&req)
	resp, _ := http.Get(req.URL)
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
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	defer session.Destroy()
	c.JSON(http.StatusOK, gin.H{"status": "loaded"})
}
