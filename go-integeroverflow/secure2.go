package main

import (
	"math"
	"net/http"
	"strconv"
)

func narrowingCastSafe(w http.ResponseWriter, r *http.Request) {
	val, _ := strconv.ParseInt(r.URL.Query().Get("value"), 10, 64)
	if val > math.MaxInt32 || val < math.MinInt32 {
		http.Error(w, "value out of range", http.StatusBadRequest)
		return
	}
	id := int32(val)
	w.Write([]byte(strconv.Itoa(int(id))))
}
