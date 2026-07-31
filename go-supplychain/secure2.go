package main

import (
	"crypto/sha256"
	"fmt"
	"io"
	"net/http"
	"os"

	ort "github.com/yalue/onnxruntime_go"
)

const detectorURL = "https://models.example.com/v2/detector.onnx"
const expectedHash = "abc123def456abc123def456abc123def456abc123def456abc123def456abcd"

func loadHandler(w http.ResponseWriter, r *http.Request) {
	resp, _ := http.Get(detectorURL)
	defer resp.Body.Close()
	modelBytes, _ := io.ReadAll(resp.Body)
	if fmt.Sprintf("%x", sha256.Sum256(modelBytes)) != expectedHash {
		http.Error(w, "integrity check failed", http.StatusBadRequest)
		return
	}
	tmpFile, _ := os.CreateTemp("", "model-*.onnx")
	tmpFile.Write(modelBytes)
	tmpFile.Close()
	session, err := ort.NewAdvancedSession(
		tmpFile.Name(),
		[]string{"input"},
		[]string{"output"},
		nil,
		nil,
		nil,
	)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	defer session.Destroy()
	w.Write([]byte("loaded"))
}
