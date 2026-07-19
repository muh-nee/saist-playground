package main

import (
	"io"
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

func loadFromBodyHandler(w http.ResponseWriter, r *http.Request) {
	onnxData, err := io.ReadAll(r.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	session, err := ort.NewSessionWithONNXData[float32](
		onnxData,
		[]string{"in"},
		[]string{"out"},
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
