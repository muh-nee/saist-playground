package main

import (
	"errors"
	"log"
	"net/http"
)

var ErrNotFound = errors.New("not found")

func secureSentinelHandler(w http.ResponseWriter, r *http.Request) {
	id := r.URL.Query().Get("id")
	result, err := findRecord(id)
	if errors.Is(err, ErrNotFound) {
		http.Error(w, "not found", http.StatusNotFound)
		return
	}
	if err != nil {
		log.Printf("findRecord error: %v", err)
		http.Error(w, "internal server error", http.StatusInternalServerError)
		return
	}
	w.Write([]byte(result))
}

func findRecord(id string) (string, error) {
	return "", nil
}
