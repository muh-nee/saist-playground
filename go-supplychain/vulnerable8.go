package main

import (
	"io"
	"net/http"
	"os"

	"github.com/go-chi/chi/v5"
	ort "github.com/yalue/onnxruntime_go"
)

func chiLoadHandler(w http.ResponseWriter, r *http.Request) {
	modelURL := chi.URLParam(r, "model_url")
	resp, err := http.Get(modelURL)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	defer resp.Body.Close()
	tmpFile, _ := os.CreateTemp("", "model-*.onnx")
	io.Copy(tmpFile, resp.Body)
	tmpFile.Close()
	session, err := ort.NewSession[float32](
		tmpFile.Name(),
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
