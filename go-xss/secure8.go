package main

import (
	"html"
	"html/template"
	"net/http"
	"regexp"
	"strings"

	"github.com/gobuffalo/buffalo"
	"github.com/gobuffalo/buffalo/render"
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

func securityHeaders(next buffalo.Handler) buffalo.Handler {
	return func(c buffalo.Context) error {
		c.Response().Header().Set("X-Content-Type-Options", "nosniff")
		c.Response().Header().Set("X-Frame-Options", "DENY")
		c.Response().Header().Set("X-XSS-Protection", "1; mode=block")
		c.Response().Header().Set("Content-Security-Policy", "default-src 'self'; script-src 'self'")
		return next(c)
	}
}

func main() {
	app := buffalo.New(buffalo.Options{
		Env: "development",
	})
	
	app.Use(securityHeaders)

	app.GET("/posts", func(c buffalo.Context) error {
		title := c.Param("title")
		author := c.Param("author")
		
		safeTitle := sanitizeInput(title)
		safeAuthor := sanitizeInput(author)
		
		tmpl := template.Must(template.New("posts").Parse(`<html><body>
		<h1>Blog Post</h1>
		<div class="post-header">
			<h2>{{.Title}}</h2>
			<p>By: <span class="author">{{.Author}}</span></p>
		</div>
		<div class="post-content">
			<p>This is a sample blog post content.</p>
		</div>
		</body></html>`))
		
		data := struct {
			Title  string
			Author string
		}{
			Title:  safeTitle,
			Author: safeAuthor,
		}
		
		c.Response().Header().Set("Content-Type", "text/html; charset=utf-8")
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		return c.Render(http.StatusOK, render.String(buf.String()))
	})

	app.POST("/contact", func(c buffalo.Context) error {
		name := c.Param("name")
		email := c.Param("email")
		subject := c.Param("subject")
		message := c.Param("message")
		
		safeName := sanitizeInput(name)
		safeEmail := sanitizeInput(email)
		safeSubject := sanitizeInput(subject)
		safeMessage := sanitizeInput(message)
		
		emailRe := regexp.MustCompile(`^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$`)
		if !emailRe.MatchString(safeEmail) {
			safeEmail = "Invalid email format"
		}
		
		if len(safeName) > 100 {
			safeName = safeName[:100]
		}
		if len(safeSubject) > 200 {
			safeSubject = safeSubject[:200]
		}
		if len(safeMessage) > 1000 {
			safeMessage = safeMessage[:1000] + "..."
		}
		
		tmpl := template.Must(template.New("contact").Parse(`<html><body>
		<h1>Contact Form Received</h1>
		<div class="contact-summary">
			<p><strong>Name:</strong> {{.Name}}</p>
			<p><strong>Email:</strong> {{.Email}}</p>
			<p><strong>Subject:</strong> {{.Subject}}</p>
			<p><strong>Message:</strong></p>
			<div class="message-box" style="border: 1px solid #ccc; padding: 10px;">
				{{.Message}}
			</div>
		</div>
		<script>
		var userName = {{.Name | js}};
		console.log("Message received from:", userName);
		document.title = "Contact - " + userName;
		</script>
		</body></html>`))
		
		data := struct {
			Name    string
			Email   string
			Subject string
			Message string
		}{
			Name:    safeName,
			Email:   safeEmail,
			Subject: safeSubject,
			Message: safeMessage,
		}
		
		c.Response().Header().Set("Content-Type", "text/html; charset=utf-8")
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		return c.Render(http.StatusOK, render.String(buf.String()))
	})

	app.Serve()
}