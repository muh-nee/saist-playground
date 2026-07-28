package main

import (
	"context"
	"encoding/json"
	"net/http"

	"github.com/philippgille/chromem-go"
)

type ingestReq struct {
	Text string `json:"text"`
	ID   string `json:"id"`
}

func handleIngest(coll *chromem.Collection) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var body ingestReq
		json.NewDecoder(r.Body).Decode(&body)
		coll.AddDocument(context.Background(), chromem.Document{
			ID:      body.ID,
			Content: body.Text,
		})
		w.WriteHeader(http.StatusOK)
	}
}
