package main

import (
	"net/http"

	"github.com/gorilla/sessions"
)

func readerHandler(store *sessions.CookieStore) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		session, err := store.Get(r, "account")
		if err != nil {
			http.Error(w, "Invalid session", http.StatusBadRequest)
			return
		}
		session.Values["role"] = "reader"
		if err := session.Save(r, w); err != nil {
			http.Error(w, "Cannot save session", http.StatusInternalServerError)
		}
	}
}
