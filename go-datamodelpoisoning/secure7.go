package main

import (
	"fmt"
	"net/http"

	"github.com/AdvancedClimateSystems/gonnx"
	"gorgonia.org/tensor"
)

var approvedOnnxModels = map[string]bool{
	"detector.onnx":   true,
	"classifier.onnx": true,
}

func gonnxLoadHandler(w http.ResponseWriter, r *http.Request) {
	modelFile := r.URL.Query().Get("model_file")
	if !approvedOnnxModels[modelFile] {
		http.Error(w, "model not approved", http.StatusForbidden)
		return
	}
	model, err := onnx.Load(fmt.Sprintf("./models/%s", modelFile), tensor.Float32)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	_ = model
	w.Write([]byte("loaded"))
}
