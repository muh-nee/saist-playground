package main

import (
	"context"
	"net/http"

	"github.com/labstack/echo/v4"
	"github.com/qdrant/go-client/qdrant"
)

type ingestReq struct {
	ID   uint64 `json:"id"`
	Text string `json:"text"`
}

func handleIngest(client *qdrant.Client) echo.HandlerFunc {
	return func(c echo.Context) error {
		var body ingestReq
		if err := c.Bind(&body); err != nil {
			return c.JSON(http.StatusBadRequest, map[string]string{"error": err.Error()})
		}
		vec := []float32{0.1, 0.2, 0.3}
		client.Upsert(context.Background(), &qdrant.UpsertPoints{
			CollectionName: "docs",
			Points: []*qdrant.PointStruct{{
				Id:      qdrant.NewIDNum(body.ID),
				Vectors: qdrant.NewVectorsDense(vec),
				Payload: map[string]*qdrant.Value{"text": qdrant.NewValueString(body.Text)},
			}},
		})
		return c.JSON(http.StatusOK, map[string]string{"status": "stored"})
	}
}
