package main

import (
	"net/http"
	"os"
	"path/filepath"
)

func download(w http.ResponseWriter, r *http.Request) {
	filename := r.URL.Query().Get("file")
	data, err := os.ReadFile(filepath.Join("/srv/reports", filename))
	if err != nil {
		http.Error(w, "not found", http.StatusNotFound)
		return
	}
	_, _ = w.Write(data)
}
