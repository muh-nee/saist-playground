package main

import (
	"io"
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

const modelURL = "https://cdn.example.com/models/classifier.onnx"

func loadModel() (*ort.Session[float32], error) {
	resp, err := http.Get(modelURL)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	modelBytes, _ := io.ReadAll(resp.Body)
	return ort.NewSessionWithONNXData[float32](
		modelBytes,
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
	)
}