package main

import (
	"html"
	"html/template"
	"net/http"
	"regexp"
	"strings"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

func sanitizeInput(input string) string {
	scriptRe := regexp.MustCompile(`(?i)<script[^>]*>.*?</script>`)
	cleaned := scriptRe.ReplaceAllString(input, "")
	
	dangerousRe := regexp.MustCompile(`(?i)<(iframe|object|embed|link|meta|style)[^>]*>.*?</\1>`)
	cleaned = dangerousRe.ReplaceAllString(cleaned, "")
	
	attrRe := regexp.MustCompile(`(?i)\s(on\w+|javascript:|data:)\s*=\s*["'][^"']*["']`)
	cleaned = attrRe.ReplaceAllString(cleaned, "")
	
	return html.EscapeString(cleaned)
}

func main() {
	r := chi.NewRouter()
	r.Use(middleware.Logger)
	r.Use(middleware.Recoverer)
	
	r.Use(func(next http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			w.Header().Set("X-Content-Type-Options", "nosniff")
			w.Header().Set("X-Frame-Options", "DENY")
			w.Header().Set("X-XSS-Protection", "1; mode=block")
			w.Header().Set("Content-Security-Policy", "default-src 'self'; script-src 'self'")
			next.ServeHTTP(w, r)
		})
	})

	r.Get("/message", func(w http.ResponseWriter, r *http.Request) {
		msg := r.URL.Query().Get("text")
		sender := r.URL.Query().Get("from")
		
		safeMsg := sanitizeInput(msg)
		safeSender := sanitizeInput(sender)
		
		tmpl := template.Must(template.New("message").Parse(`<html><body>
		<h1>Message Center</h1>
		<div class="message-card">
			<div class="sender">From: {{.Sender}}</div>
			<div class="content">{{.Message}}</div>
		</div>
		<style>
		.message-card { border: 1px solid #ddd; padding: 15px; margin: 10px; }
		.sender { font-weight: bold; color: #555; }
		.content { margin-top: 10px; }
		</style>
		</body></html>`))
		
		data := struct {
			Message string
			Sender  string
		}{
			Message: safeMsg,
			Sender:  safeSender,
		}
		
		w.Header().Set("Content-Type", "text/html; charset=utf-8")
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		w.Write([]byte(buf.String()))
	})

	r.Get("/error", func(w http.ResponseWriter, r *http.Request) {
		errorMsg := r.URL.Query().Get("msg")
		
		safeErrorMsg := sanitizeInput(errorMsg)
		
		if len(safeErrorMsg) > 200 {
			safeErrorMsg = safeErrorMsg[:200] + "..."
		}
		
		tmpl := template.Must(template.New("error").Parse(`<html><body>
		<h1 style="color: red;">Error Occurred</h1>
		<div class="error-box" style="background: #ffe6e6; padding: 20px; border: 1px solid red;">
			<p>The following error occurred:</p>
			<pre>{{.ErrorMsg}}</pre>
		</div>
		<a href="/">Go back to home</a>
		</body></html>`))
		
		data := struct {
			ErrorMsg string
		}{
			ErrorMsg: safeErrorMsg,
		}
		
		w.Header().Set("Content-Type", "text/html; charset=utf-8")
		w.WriteHeader(http.StatusBadRequest)
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		w.Write([]byte(buf.String()))
	})

	http.ListenAndServe(":3000", r)
}