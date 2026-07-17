package main

import (
	"context"
	"database/sql"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/pgvector/pgvector-go"
)

type ingestReq struct {
	Text string `json:"text"`
}

func setupRouter(db *sql.DB) *gin.Engine {
	r := gin.New()
	r.POST("/ingest", func(c *gin.Context) {
		var body ingestReq
		if err := c.ShouldBindJSON(&body); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		vec := pgvector.NewVector([]float32{0.1, 0.2, 0.3})
		db.ExecContext(context.Background(),
			"INSERT INTO docs (content, embedding) VALUES ($1, $2)",
			body.Text, vec,
		)
		c.JSON(http.StatusOK, gin.H{"status": "stored"})
	})
	return r
}
