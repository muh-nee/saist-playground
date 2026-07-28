package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
	torch "github.com/orktes/go-torch"
)

type TorchRequest struct {
	ModelPath string `json:"model_path"`
}

func torchLoadHandler(c *gin.Context) {
	var req TorchRequest
	c.ShouldBindJSON(&req)
	module, err := torch.LoadJITModule(req.ModelPath)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	defer module.Close()
	c.JSON(http.StatusOK, gin.H{"status": "loaded"})
}
