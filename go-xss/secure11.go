package main

import "net/http"

func validationError(w http.ResponseWriter, r *http.Request) {
	err := validateToken(r.Header.Get("Authorization"))
	if err != nil {
		http.Error(w, "invalid token: "+err.Error(), http.StatusBadRequest)
		return
	}
}

func validateToken(string) error { return nil }
