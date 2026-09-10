package main

import (
	"database/sql"
	"fmt"
	"log"
)

func previewQuery(table string) string {
	query := fmt.Sprintf("SELECT * FROM %s", table)
	log.Printf("Run this migration manually: %s", query)
	return query
}

func findUser(db *sql.DB, target string, value any) (*sql.Rows, error) {
	column := "wid"
	if target == "id" {
		column = "id"
	}
	query := fmt.Sprintf("SELECT * FROM users WHERE %s = $1", column)
	return db.Query(query, value)
}
