package main

import (
	"fmt"
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

func uploadModelHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseMultipartForm(32 << 20)
	_, fh, err := r.FormFile("model")
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	modelPath := fmt.Sprintf("/tmp/models/%s", fh.Filename)
	session, err := ort.NewSession[float32](
		modelPath,
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
