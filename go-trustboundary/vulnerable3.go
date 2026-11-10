package main

import (
	"net/http"

	"github.com/gorilla/sessions"
)

func userHandler(store *sessions.CookieStore) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		cookie, err := r.Cookie("user_id")
		if err != nil {
			http.Error(w, "Missing user", http.StatusBadRequest)
			return
		}
		session, err := store.Get(r, "account")
		if err != nil {
			http.Error(w, "Invalid session", http.StatusBadRequest)
			return
		}
		session.Values["user_id"] = cookie.Value
		if err := session.Save(r, w); err != nil {
			http.Error(w, "Cannot save session", http.StatusInternalServerError)
		}
	}
}
