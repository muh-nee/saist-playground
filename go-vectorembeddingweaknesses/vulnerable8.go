package main

import (
	"context"
	"net/http"

	"github.com/go-chi/chi/v5"
	"github.com/philippgille/chromem-go"
)

func setupRouter(coll *chromem.Collection) http.Handler {
	r := chi.NewRouter()
	r.Post("/ingest", func(w http.ResponseWriter, r *http.Request) {
		text := r.FormValue("text")
		id := r.FormValue("id")
		coll.AddDocuments(context.Background(), []chromem.Document{
			{ID: id, Content: text},
		}, 1)
		w.WriteHeader(http.StatusOK)
	})
	return r
}
