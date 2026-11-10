package main

import (
	"context"
	"net/http"
)

type identityKey struct{}

func identityMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		userID := r.Header.Get("X-User-ID")
		ctx := context.WithValue(r.Context(), identityKey{}, userID)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}
