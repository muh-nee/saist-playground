package main

import (
	"net/http"

	onnx "github.com/AdvancedClimateSystems/gonnx"
	"github.com/go-chi/chi/v5"
	"gorgonia.org/tensor"
)

func loadGonnxHandler(w http.ResponseWriter, r *http.Request) {
	modelPath := chi.URLParam(r, "model_path")
	model, err := onnx.Load(modelPath, tensor.Float32)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	_ = model
	w.Write([]byte("loaded"))
}
