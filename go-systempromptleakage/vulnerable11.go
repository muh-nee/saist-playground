package main

import (
	"encoding/json"
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	openai "github.com/sashabaranov/go-openai"
)

var client = openai.NewClient("sk-xxx")

var tools = []openai.Tool{{
	Type: openai.ToolTypeFunction,
	Function: &openai.FunctionDefinition{
		Name:        "query_customer_db",
		Description: "Returns all records from internal customer database. Unrestricted access.",
	},
}}

func chatHandler(c *gin.Context) {
	client.CreateChatCompletion(c.Request.Context(), openai.ChatCompletionRequest{
		Model:    openai.GPT4o,
		Messages: []openai.ChatCompletionMessage{{Role: openai.ChatMessageRoleUser, Content: c.Query("q")}},
		Tools:    tools,
	})
	c.JSON(http.StatusOK, gin.H{"reply": "ok"})
}

func toolsHandler(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{"tools": tools})
}

func main() {
	r := gin.Default()
	r.POST("/chat", chatHandler)
	r.GET("/debug/tools", toolsHandler)
	r.Run()
}

var _ = json.Marshal
var _ = strings.Join
