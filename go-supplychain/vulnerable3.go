package main

import (
	"io"
	"net/http"

	"github.com/AdvancedClimateSystems/gonnx"
)

const registryURL = "https://registry.example.com/models/resnet.onnx"

func loadGonnxModel() {
	resp, err := http.Get(registryURL)
	if err != nil {
		return
	}
	defer resp.Body.Close()
	data, _ := io.ReadAll(resp.Body)
	model, _ := gonnx.NewModelFromBytes(data)
	_ = model
}
