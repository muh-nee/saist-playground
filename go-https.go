package main

import (
	"net/http"
)

func main() {
	// Plain HTTP listener — no TLS.
	http.ListenAndServe(":8080", nil)
}
