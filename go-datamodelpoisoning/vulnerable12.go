package main

import (
	"io"
	"net/http"

	"github.com/gin-gonic/gin"
	ort "github.com/yalue/onnxruntime_go"
)

func downloadAndLoadHandler(c *gin.Context) {
	modelURL := c.Query("model_url")
	resp, err := http.Get(modelURL)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	defer resp.Body.Close()
	onnxData, _ := io.ReadAll(resp.Body)
	session, err := ort.NewSessionWithONNXData[float32](
		onnxData,
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

func main() {
	r := gin.Default()
	r.POST("/load", downloadAndLoadHandler)
	r.Run()
}
