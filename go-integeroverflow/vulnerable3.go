package main

import (
	"net/http"
	"strconv"
)

const blockSize = 1024

func processRequest(w http.ResponseWriter, r *http.Request) {
	n, _ := strconv.Atoi(r.FormValue("n"))
	total := n * blockSize // n*blockSize may overflow if n is large
	data := make([]byte, total)
	w.Write(data)
}
