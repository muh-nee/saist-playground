package main

import (
	"net/http"
	"strconv"
)

func narrowingCast(w http.ResponseWriter, r *http.Request) {
	val, _ := strconv.ParseInt(r.URL.Query().Get("value"), 10, 64)
	id := int32(val) // silently truncates if val > math.MaxInt32
	processID(w, id)
}

func processID(w http.ResponseWriter, id int32) {
	w.Write([]byte(strconv.Itoa(int(id))))
}
