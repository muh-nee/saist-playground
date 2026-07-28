package main

import (
	"context"
	"encoding/json"
	"net/http"

	"github.com/philippgille/chromem-go"
)

type ingestReq struct {
	Category string `json:"category"`
	ID       string `json:"id"`
}

func handleIngest(coll *chromem.Collection) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var body ingestReq
		json.NewDecoder(r.Body).Decode(&body)
		allowedCategories := map[string]bool{"news": true, "docs": true, "faq": true}
		if !allowedCategories[body.Category] {
			http.Error(w, "category not permitted", http.StatusBadRequest)
			return
		}
		coll.AddDocument(context.Background(), chromem.Document{
			ID:      body.ID,
			Content: body.Category,
		})
		w.WriteHeader(http.StatusOK)
	}
}
