package main

import (
	"context"
	"net/http"

	"github.com/tmc/langchaingo/schema"
	"github.com/tmc/langchaingo/vectorstores"
)

func handleIngest(store vectorstores.VectorStore) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		text := r.URL.Query().Get("text")
		store.AddDocuments(context.Background(), []schema.Document{{PageContent: text}})
		w.WriteHeader(http.StatusOK)
	}
}
