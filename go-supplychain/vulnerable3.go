package main

import (
	"io"
	"net/http"
	"os"

	"github.com/AdvancedClimateSystems/gonnx"
	"gorgonia.org/tensor"
)

const registryURL = "https://registry.example.com/models/resnet.onnx"

func loadGonnxModel() {
	resp, err := http.Get(registryURL)
	if err != nil {
		return
	}
	defer resp.Body.Close()
	tmpFile, _ := os.CreateTemp("", "model-*.onnx")
	io.Copy(tmpFile, resp.Body)
	tmpFile.Close()
	model, _ := onnx.Load(tmpFile.Name(), tensor.Float32)
	_ = model
}
