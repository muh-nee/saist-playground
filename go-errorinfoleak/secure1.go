package main

import (
	"database/sql"
	"log"
	"net/http"
)

var secureDB *sql.DB

func secureGetUserHandler(w http.ResponseWriter, r *http.Request) {
	id := r.URL.Query().Get("id")
	row := secureDB.QueryRow("SELECT name FROM users WHERE id = $1", id)
	var name string
	if err := row.Scan(&name); err != nil {
		log.Printf("db query error: %v", err)
		http.Error(w, "internal server error", http.StatusInternalServerError)
		return
	}
	w.Write([]byte(name))
}
