package main

import (
	"context"
	"encoding/json"
	"net/http"

	"github.com/milvus-io/milvus-sdk-go/v2/client"
	"github.com/milvus-io/milvus-sdk-go/v2/entity"
)

type ingestReq struct {
	Text string `json:"text"`
}

func handleIngest(mc client.Client) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var body ingestReq
		json.NewDecoder(r.Body).Decode(&body)
		mc.Insert(context.Background(), "docs", "", entity.NewColumnVarChar("content", []string{body.Text}))
		w.WriteHeader(http.StatusOK)
	}
}
