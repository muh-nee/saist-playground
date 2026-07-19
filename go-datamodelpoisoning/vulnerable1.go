package main

import (
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

func loadModelHandler(w http.ResponseWriter, r *http.Request) {
	modelPath := r.URL.Query().Get("model_path")
	session, err := ort.NewSession[float32](
		modelPath,
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
