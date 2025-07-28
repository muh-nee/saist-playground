package main

import (
	"fmt"
	"net/http"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

func main() {
	r := chi.NewRouter()
	r.Use(middleware.Logger)

	r.Get("/message", func(w http.ResponseWriter, r *http.Request) {
		msg := r.URL.Query().Get("text")
		sender := r.URL.Query().Get("from")
		
		html := fmt.Sprintf(`<html><body>
		<h1>Message Center</h1>
		<div class="message-card">
			<div class="sender">From: %s</div>
			<div class="content">%s</div>
		</div>
		<style>
		.message-card { border: 1px solid #ddd; padding: 15px; margin: 10px; }
		.sender { font-weight: bold; color: #555; }
		.content { margin-top: 10px; }
		</style>
		</body></html>`, sender, msg)
		
		w.Header().Set("Content-Type", "text/html")
		w.Write([]byte(html))
	})

	r.Get("/error", func(w http.ResponseWriter, r *http.Request) {
		errorMsg := r.URL.Query().Get("msg")
		
		html := fmt.Sprintf(`<html><body>
		<h1 style="color: red;">Error Occurred</h1>
		<div class="error-box" style="background: #ffe6e6; padding: 20px; border: 1px solid red;">
			<p>The following error occurred:</p>
			<pre>%s</pre>
		</div>
		<a href="/">Go back to home</a>
		</body></html>`, errorMsg)
		
		w.Header().Set("Content-Type", "text/html")
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte(html))
	})

	http.ListenAndServe(":3000", r)
}