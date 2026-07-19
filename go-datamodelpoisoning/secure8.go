package main

import (
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

func adminOnly(next http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		role, _ := r.Context().Value("role").(string)
		if role != "admin" {
			http.Error(w, "forbidden", http.StatusForbidden)
			return
		}
		next(w, r)
	}
}

var loadModelHandler http.HandlerFunc = adminOnly(func(w http.ResponseWriter, r *http.Request) {
	modelPath := r.URL.Query().Get("model_path")
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
	w.Write([]byte("loaded"))
})
