package main

import (
	"context"
	"net/http"

	"github.com/milvus-io/milvus-sdk-go/v2/client"
	"github.com/milvus-io/milvus-sdk-go/v2/entity"
)

func handleIngest(mc client.Client) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		staticContent := []string{"Go programming guide", "API documentation"}
		mc.Insert(context.Background(), "docs", "", entity.NewColumnVarChar("content", staticContent))
		w.WriteHeader(http.StatusOK)
	}
}
