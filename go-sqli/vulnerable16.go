package main

import (
	"database/sql"
	"fmt"
	"net/http"
)

func searchUsers(db *sql.DB, w http.ResponseWriter, r *http.Request) {
	column := r.URL.Query().Get("column")
	query := fmt.Sprintf("SELECT * FROM users ORDER BY %s", column)
	_, _ = db.Query(query)
}
