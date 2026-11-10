package main

import (
	"net/http"

	"github.com/gorilla/sessions"
)

func roleHandler(store *sessions.CookieStore) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		session, err := store.Get(r, "account")
		if err != nil {
			http.Error(w, "Invalid session", http.StatusBadRequest)
			return
		}
		session.Values["role"] = r.FormValue("role")
		if err := session.Save(r, w); err != nil {
			http.Error(w, "Cannot save session", http.StatusInternalServerError)
		}
	}
}
