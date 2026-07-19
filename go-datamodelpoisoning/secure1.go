package main

import (
	"context"
	"log"

	ort "github.com/yalue/onnxruntime_go"
)

const modelPath = "./models/classifier_v2.onnx"

func initSession(ctx context.Context) (*ort.AdvancedSession, error) {
	session, err := ort.NewAdvancedSession(
		modelPath,
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
		nil,
	)
	if err != nil {
		log.Printf("failed to load model: %v", err)
		return nil, err
	}
	return session, nil
}
