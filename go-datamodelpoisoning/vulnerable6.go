package main

import (
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

func updateModelHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	uploadedPath := r.FormValue("model_file_path")
	resolvedPath := "/models/" + uploadedPath
	session, err := ort.NewSession[float32](
		resolvedPath,
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
	w.Write([]byte("ok"))
}
