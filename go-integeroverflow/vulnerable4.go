package main

import (
	"net/http"
	"strconv"
)

func sliceIndex(w http.ResponseWriter, r *http.Request) {
	data := make([]byte, 1024)
	offset, _ := strconv.Atoi(r.URL.Query().Get("offset"))
	length, _ := strconv.Atoi(r.URL.Query().Get("length"))
	end := offset + length // may overflow to negative, bypassing bounds check
	if end < len(data) {
		w.Write(data[offset:end])
	}
}
