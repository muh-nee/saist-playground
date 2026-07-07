package main

import (
	"net/http"
	"strconv"
)

func allocateBuffer(w http.ResponseWriter, r *http.Request) {
	count, _ := strconv.Atoi(r.URL.Query().Get("count"))
	size, _ := strconv.Atoi(r.URL.Query().Get("size"))
	buf := make([]byte, count*size) // count*size may overflow int
	w.Write(buf)
}
