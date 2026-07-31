package main

import (
	"crypto/sha256"
	"fmt"
	"io"
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

const modelURL = "https://cdn.example.com/models/classifier.onnx"
const expectedModelHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

func loadModel(w http.ResponseWriter, r *http.Request) {
	resp, err := http.Get(modelURL)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	defer resp.Body.Close()
	modelBytes, _ := io.ReadAll(resp.Body)
	if fmt.Sprintf("%x", sha256.Sum256(modelBytes)) != expectedModelHash {
		http.Error(w, "model integrity check failed", http.StatusBadRequest)
		return
	}
	session, err := ort.NewSessionWithONNXData[float32](
		modelBytes,
		[]string{"input"},
		[]string{"output"},
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
