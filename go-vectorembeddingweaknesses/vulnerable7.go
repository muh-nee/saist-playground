package main

import (
	"context"
	"encoding/json"
	"net/http"

	"github.com/pinecone-io/go-pinecone/pinecone"
	"google.golang.org/protobuf/types/known/structpb"
)

type ingestReq struct {
	Text string `json:"text"`
}

func handleIngest(idxConn *pinecone.IndexConnection) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var body ingestReq
		json.NewDecoder(r.Body).Decode(&body)
		meta, _ := structpb.NewStruct(map[string]interface{}{"text": body.Text})
		idxConn.UpsertVectors(context.Background(), []*pinecone.Vector{{
			Id:       "doc1",
			Values:   &[]float32{0.1, 0.2, 0.3},
			Metadata: meta,
		}})
		w.WriteHeader(http.StatusOK)
	}
}
