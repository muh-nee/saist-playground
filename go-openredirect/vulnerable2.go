package main

import (
	"net/http"
	"strings"
)

// Vulnerable: startsWith("/") check bypassed by scheme-relative URL like //evil.com
func redirectHandler(w http.ResponseWriter, r *http.Request) {
	target := r.FormValue("redirect")

	// VULNERABLE: //evil.com/path starts with "/" but redirects to evil.com
	if strings.HasPrefix(target, "/") {
		http.Redirect(w, r, target, http.StatusFound)
		return
	}
	http.Redirect(w, r, "/", http.StatusFound)
}

func main() {
	http.HandleFunc("/go", redirectHandler)
	http.ListenAndServe(":8080", nil)
}
