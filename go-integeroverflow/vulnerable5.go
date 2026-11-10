package main

import (
	"encoding/json"
	"net/http"
)

type PaginationRequest struct {
	Page     int `json:"page"`
	PageSize int `json:"page_size"`
}

func paginate(w http.ResponseWriter, r *http.Request) {
	var req PaginationRequest
	json.NewDecoder(r.Body).Decode(&req)
	offset := req.Page * req.PageSize // may overflow; Page and PageSize are user-controlled
	fetchRows(w, offset)
}

var rows [1000][]byte

func fetchRows(w http.ResponseWriter, offset int) {
	w.Write(rows[offset])
}
