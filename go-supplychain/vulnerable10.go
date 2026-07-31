package main

import (
	"io"
	"net/http"
	"os"

	ort "github.com/yalue/onnxruntime_go"
)

func loadModelFromEnv() (*ort.Session[float32], error) {
	modelURL := os.Getenv("MODEL_DOWNLOAD_URL")
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
