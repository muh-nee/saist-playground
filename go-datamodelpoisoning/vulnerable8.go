package main

import (
	"net/http"
	"path/filepath"

	ort "github.com/yalue/onnxruntime_go"
)

func cleanedLoadHandler(w http.ResponseWriter, r *http.Request) {
	modelPath := r.URL.Query().Get("model_path")
	cleanPath := filepath.Clean(modelPath)
	session, err := ort.NewSession[float32](
		cleanPath,
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
	)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	defer session.Destroy()
	w.Write([]byte("loaded"))
}
