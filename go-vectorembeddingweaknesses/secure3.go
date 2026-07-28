package main

import (
	"context"
	"net/http"
	"strings"

	"github.com/golang-jwt/jwt/v5"
	"github.com/labstack/echo/v4"
	"github.com/qdrant/go-client/qdrant"
)

type ingestReq struct {
	ID   uint64 `json:"id"`
	Text string `json:"text"`
}

var jwtSecret = []byte("supersecret")

func handleIngest(client *qdrant.Client) echo.HandlerFunc {
	return func(c echo.Context) error {
		authHeader := c.Request().Header.Get("Authorization")
		if !strings.HasPrefix(authHeader, "Bearer ") {
			return c.JSON(http.StatusUnauthorized, map[string]string{"error": "missing token"})
		}
		tokenStr := strings.TrimPrefix(authHeader, "Bearer ")
		token, err := jwt.Parse(tokenStr, func(t *jwt.Token) (interface{}, error) {
			return jwtSecret, nil
		})
		if err != nil || !token.Valid {
			return c.JSON(http.StatusUnauthorized, map[string]string{"error": "invalid token"})
		}
		claims, ok := token.Claims.(jwt.MapClaims)
		if !ok {
			return c.JSON(http.StatusUnauthorized, map[string]string{"error": "invalid claims"})
		}
		sub, _ := claims["sub"].(string)
		collectionName := "user_" + sub

		var body ingestReq
		if err := c.Bind(&body); err != nil {
			return c.JSON(http.StatusBadRequest, map[string]string{"error": err.Error()})
		}
		vec := []float32{0.1, 0.2, 0.3}
		client.Upsert(context.Background(), &qdrant.UpsertPoints{
			CollectionName: collectionName,
			Points: []*qdrant.PointStruct{{
				Id:      qdrant.NewIDNum(body.ID),
				Vectors: qdrant.NewVectorsDense(vec),
				Payload: map[string]*qdrant.Value{"text": qdrant.NewValueString(body.Text)},
			}},
		})
		return c.JSON(http.StatusOK, map[string]string{"status": "stored"})
	}
}
