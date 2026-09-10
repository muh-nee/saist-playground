package main

import (
	"net/http"

	openai "github.com/sashabaranov/go-openai"
)

func embedSearchText(w http.ResponseWriter, r *http.Request, client *openai.Client) {
	searchText := r.FormValue("search")
	_, _ = client.CreateEmbeddings(r.Context(), openai.EmbeddingRequest{
		Model: openai.AdaEmbeddingV2,
		Input: []string{searchText},
	})
}
