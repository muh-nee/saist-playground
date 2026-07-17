package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/weaviate/weaviate-go-client/v4/weaviate"
	"github.com/weaviate/weaviate/entities/models"
)

type ingestReq struct {
	Text string `json:"text"`
}

func setupRouter(client *weaviate.Client) *gin.Engine {
	r := gin.New()
	r.POST("/ingest", func(c *gin.Context) {
		var body ingestReq
		if err := c.ShouldBindJSON(&body); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		client.Batch().ObjectsBatcher().
			WithObjects(&models.Object{
				Class:      "Article",
				Properties: map[string]interface{}{"content": body.Text},
			}).
			Do(context.Background())
		c.JSON(http.StatusOK, gin.H{"status": "stored"})
	})
	return r
}
