package main

import (
	"net/http"
)

var allowedRedirects = map[string]bool{
	"/dashboard": true,
	"/profile":   true,
	"/settings":  true,
	"/home":      true,
}

// Safe: allowlist of permitted redirect destinations
func afterLoginHandler(w http.ResponseWriter, r *http.Request) {
	next := r.URL.Query().Get("next")

	// Only redirect to explicitly allowed destinations
	if !allowedRedirects[next] {
		next = "/dashboard"
	}
	http.Redirect(w, r, next, http.StatusFound)
}

func main() {
	http.HandleFunc("/after-login", afterLoginHandler)
	http.ListenAndServe(":8080", nil)
}
