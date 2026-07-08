package main

import (
	"net/http"
)

// Vulnerable: user-controlled query parameter passed directly to http.Redirect
func loginHandler(w http.ResponseWriter, r *http.Request) {
	username := r.FormValue("username")
	password := r.FormValue("password")

	if authenticate(username, password) {
		next := r.URL.Query().Get("next")
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
