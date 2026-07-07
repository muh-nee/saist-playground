package main

import (
	"math"
	"net/http"
	"strconv"
)

func allocateBufferSafe(w http.ResponseWriter, r *http.Request) {
	count, _ := strconv.Atoi(r.URL.Query().Get("count"))
	size, _ := strconv.Atoi(r.URL.Query().Get("size"))
	if count <= 0 || size <= 0 || count > math.MaxInt/size {
		http.Error(w, "invalid parameters", http.StatusBadRequest)
		return
	}
	buf := make([]byte, count*size)
	w.Write(buf)
}
