package main

import (
	"context"
	"database/sql"
	"encoding/json"
	"net/http"
	"strings"

	"github.com/golang-jwt/jwt/v5"
	"github.com/pgvector/pgvector-go"
)

var jwtSecret = []byte("secret")

type roleKey struct{}

type ingestReq struct {
	Text string `json:"text"`
}

func roleMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		header := r.Header.Get("Authorization")
		tokenStr := strings.TrimPrefix(header, "Bearer ")
		token, err := jwt.Parse(tokenStr, func(t *jwt.Token) (interface{}, error) { return jwtSecret, nil })
		if err != nil || !token.Valid {
			http.Error(w, "unauthorized", http.StatusUnauthorized)
			return
		}
		claims := token.Claims.(jwt.MapClaims)
		role, _ := claims["role"].(string)
		ctx := context.WithValue(r.Context(), roleKey{}, role)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func handleIngest(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		role, _ := r.Context().Value(roleKey{}).(string)
		if role != "admin" {
			http.Error(w, "forbidden", http.StatusForbidden)
			return
		}
		var body ingestReq
		if err := json.NewDecoder(r.Body).Decode(&body); err != nil {
			http.Error(w, "bad request", http.StatusBadRequest)
			return
		}
		vec := pgvector.NewVector([]float32{0.1, 0.2, 0.3})
		db.ExecContext(context.Background(),
			"INSERT INTO docs (content, embedding) VALUES ($1, $2)",
			body.Text, vec,
		)
		w.WriteHeader(http.StatusOK)
	}
}
