package main

import (
	"net/http"
	"net/url"
)

// Safe: parse URL and verify no host component before redirecting
func loginHandler(w http.ResponseWriter, r *http.Request) {
	username := r.FormValue("username")
	password := r.FormValue("password")

	if authenticate(username, password) {
		next := r.URL.Query().Get("next")
		u, err := url.Parse(next)
		if err != nil || u.Host != "" || u.Scheme != "" {
			// URL has a host or scheme — redirect to safe default instead
			next = "/dashboard"
		}
		http.Redirect(w, r, next, http.StatusFound)
		return
	}
	http.Error(w, "Unauthorized", http.StatusUnauthorized)
}

func authenticate(username, password string) bool {
	return username == "admin" && password == "secret"
}

func main() {
	http.HandleFunc("/login", loginHandler)
	http.ListenAndServe(":8080", nil)
}
