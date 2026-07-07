package main

import (
	"net/http"
)

func computeArea(w http.ResponseWriter, r *http.Request) {
	// Internal state only — no user-controlled values
	width := 100
	height := 200
	area := width * height // no overflow risk; hardcoded values
	w.Write([]byte(strconv.Itoa(area)))
}
