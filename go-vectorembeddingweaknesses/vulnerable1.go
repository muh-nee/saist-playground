package main

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/tmc/langchaingo/schema"
	"github.com/tmc/langchaingo/vectorstores"
)

type ingestReq struct {
	Text string `json:"text"`
}

func setupRouter(store vectorstores.VectorStore) *gin.Engine {
	r := gin.New()
	r.POST("/ingest", func(c *gin.Context) {
		var body ingestReq
		if err := c.ShouldBindJSON(&body); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		store.AddDocuments(context.Background(), []schema.Document{{PageContent: body.Text}})
		c.JSON(http.StatusOK, gin.H{"status": "stored"})
	})
	return r
}
