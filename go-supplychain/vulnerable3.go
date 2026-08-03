package main

import (
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
	model, _ := gonnx.NewModel(resp.Body, nil)
	_ = model
}
