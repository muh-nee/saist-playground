package main

import (
	"io"
	"net/http"
	"time"

	ort "github.com/yalue/onnxruntime_go"
)

const embedderURL = "https://cdn.example.com/models/embedder.onnx"

func loadWithClient() (*ort.Session[float32], error) {
	client := &http.Client{Timeout: 30 * time.Second}
	req, _ := http.NewRequest("GET", embedderURL, nil)
	resp, err := client.Do(req)
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
