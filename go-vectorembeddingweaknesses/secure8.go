package main

import (
	"context"
	"encoding/json"
	"net/http"
	"strings"

	"github.com/golang-jwt/jwt/v5"
	"github.com/pinecone-io/go-pinecone/pinecone"
	"google.golang.org/protobuf/types/known/structpb"
)

type ingestReq struct {
	Text string `json:"text"`
}

var jwtSecret = []byte("supersecret")

func handleIngest(idxConn *pinecone.IndexConnection) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		authHeader := r.Header.Get("Authorization")
		if !strings.HasPrefix(authHeader, "Bearer ") {
			http.Error(w, "missing token", http.StatusUnauthorized)
			return
		}
		tokenStr := strings.TrimPrefix(authHeader, "Bearer ")
		token, err := jwt.Parse(tokenStr, func(t *jwt.Token) (interface{}, error) {
			return jwtSecret, nil
		})
		if err != nil || !token.Valid {
			http.Error(w, "invalid token", http.StatusUnauthorized)
			return
		}
		claims, ok := token.Claims.(jwt.MapClaims)
		if !ok || claims["role"] != "admin" {
			http.Error(w, "forbidden", http.StatusForbidden)
			return
		}
		var body ingestReq
		json.NewDecoder(r.Body).Decode(&body)
		meta, _ := structpb.NewStruct(map[string]interface{}{"text": body.Text})
		idxConn.UpsertVectors(context.Background(), []*pinecone.Vector{{
			Id:       "doc1",
			Values:   &[]float32{0.1, 0.2, 0.3},
			Metadata: meta,
		}})
		w.WriteHeader(http.StatusOK)
	}
}
