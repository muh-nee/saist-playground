package main

import (
	"fmt"
	"net/http"
	"os"
)

func downloadHandler(w http.ResponseWriter, r *http.Request) {
	path := r.URL.Query().Get("path")
	f, err := os.Open(path)
	if err != nil {
		w.WriteHeader(500)
		fmt.Fprintf(w, "error opening file: %v", err)
		return
	}
	defer f.Close()
}
