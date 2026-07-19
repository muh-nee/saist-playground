package main

import (
	"crypto/sha256"
	"encoding/hex"
	"io"
	"net/http"
	"os"

	ort "github.com/yalue/onnxruntime_go"
)

const expectedModelHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

func hashVerifiedLoadHandler(w http.ResponseWriter, r *http.Request) {
	modelPath := r.URL.Query().Get("model_path")
	f, err := os.Open(modelPath)
	if err != nil {
		http.Error(w, "not found", http.StatusNotFound)
		return
	}
	defer f.Close()
	h := sha256.New()
	if _, err := io.Copy(h, f); err != nil {
		http.Error(w, "hash error", http.StatusInternalServerError)
		return
	}
	if hex.EncodeToString(h.Sum(nil)) != expectedModelHash {
		http.Error(w, "integrity check failed", http.StatusForbidden)
		return
	}
	session, err := ort.NewAdvancedSession(
		modelPath,
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
	w.Write([]byte("ok"))
}
