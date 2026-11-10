package main

import (
	"context"
	"net/http"
)

type roleKey struct{}

func readerMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		roles := []string{"reader", r.Header.Get("X-Role")}
		ctx := context.WithValue(r.Context(), roleKey{}, roles[0])
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}
