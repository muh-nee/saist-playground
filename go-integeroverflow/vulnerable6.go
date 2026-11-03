package main

import (
	"net/http"
	"strconv"
)

func allocateFromHashID(w http.ResponseWriter, r *http.Request) {
	hashID, err := strconv.ParseUint(r.URL.Query().Get("hash_id"), 10, 64)
	if err != nil {
		http.Error(w, "invalid id", http.StatusBadRequest)
		return
	}
	size := int64(hashID)
	buffer := make([]byte, size)
	w.Write(buffer)
}
