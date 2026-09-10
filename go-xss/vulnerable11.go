package main

import "net/http"

func htmlError(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	http.Error(w, r.URL.Query().Get("message"), http.StatusBadRequest)
}
