package main

import (
	"net/http"
	"path/filepath"
	"strings"

	ort "github.com/yalue/onnxruntime_go"
)

const allowedModelDir = "/opt/models"

func loadHandler(w http.ResponseWriter, r *http.Request) {
	modelName := r.URL.Query().Get("model_name")
	modelPath := filepath.Clean(filepath.Join(allowedModelDir, modelName))
	if !strings.HasPrefix(modelPath, allowedModelDir+"/") {
		http.Error(w, "invalid model path", http.StatusBadRequest)
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
