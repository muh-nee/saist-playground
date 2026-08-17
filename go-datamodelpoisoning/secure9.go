package main

import (
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"io"
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

const (
	trustedModelURL = "https://internal.example.com/models/classifier.onnx"
	expectedSHA256  = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
)

func loadVerifiedModel() (*ort.AdvancedSession, error) {
	resp, err := http.Get(trustedModelURL)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	data, err := io.ReadAll(resp.Body)
	if err != nil {
		return nil, err
	}
	sum := sha256.Sum256(data)
	if hex.EncodeToString(sum[:]) != expectedSHA256 {
		return nil, fmt.Errorf("integrity check failed")
	}
	return ort.NewAdvancedSession(
		string(data),
		[]string{"input"},
		[]string{"output"},
		nil, nil, nil,
	)
}
