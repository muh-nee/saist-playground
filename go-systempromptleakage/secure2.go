package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
	openai "github.com/sashabaranov/go-openai"
)

var client *openai.Client

const systemPrompt = "Internal assistant. Confidential business logic."

func adminPromptHandler(c *gin.Context) {
	if c.GetHeader("X-Admin-Token") != "admin-secret" {
		c.JSON(http.StatusForbidden, gin.H{"error": "forbidden"})
		return
	}
	c.JSON(http.StatusOK, gin.H{"prompt": systemPrompt})
}
