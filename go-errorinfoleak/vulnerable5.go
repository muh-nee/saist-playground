package main

import (
	"fmt"
	"net/http"
)

func recoverMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		defer func() {
			if rv := recover(); rv != nil {
				http.Error(w, fmt.Sprintf("panic: %v", rv), http.StatusInternalServerError)
			}
		}()
		next.ServeHTTP(w, r)
	})
}
