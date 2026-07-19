package main

import (
	"log"
	"os"

	ort "github.com/yalue/onnxruntime_go"
)

var globalSession *ort.AdvancedSession

func init() {
	modelPath := os.Getenv("ONNX_MODEL_PATH")
	var err error
	globalSession, err = ort.NewAdvancedSession(
		modelPath,
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
		nil,
	)
	if err != nil {
		log.Fatalf("failed to load model: %v", err)
	}
}
