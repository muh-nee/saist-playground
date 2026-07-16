package main

import (
	"fmt"
	"net/http"
	"os"
	"path/filepath"
)

func fileHandler(w http.ResponseWriter, r *http.Request) {
	baseDir := os.Getenv("APP_DATA_DIR")
	filename := r.URL.Query().Get("file")

	fullPath := filepath.Join(baseDir, filename)
	data, err := os.ReadFile(fullPath)
	if err != nil {
		http.Error(w, "file not found", http.StatusNotFound)
		return
	}

	fmt.Fprintf(w, "%s", data)
}

func main() {
	http.HandleFunc("/file", fileHandler)
	http.ListenAndServe(":8080", nil)
}
