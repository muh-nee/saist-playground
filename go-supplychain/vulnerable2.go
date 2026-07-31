package main

import (
	"io"
	"net/http"
	"os"

	ort "github.com/yalue/onnxruntime_go"
)

const detectorURL = "https://models.example.com/v2/detector.onnx"

func initSession() (*ort.AdvancedSession, error) {
	resp, err := http.Get(detectorURL)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	tmpFile, _ := os.CreateTemp("", "model-*.onnx")
	io.Copy(tmpFile, resp.Body)
	tmpFile.Close()
	return ort.NewAdvancedSession(
		tmpFile.Name(),
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
		nil,
	)
}
