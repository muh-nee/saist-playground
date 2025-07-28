package main

import (
	"html"
	"html/template"
	"net/url"
	"regexp"
	"strings"

	"github.com/kataras/iris/v12"
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

func validateURL(rawURL string) bool {
	if rawURL == "" {
		return false
	}
	
	parsedURL, err := url.Parse(rawURL)
	if err != nil {
		return false
	}
	
	if parsedURL.Scheme != "http" && parsedURL.Scheme != "https" {
		return false
	}
	
	return true
}

func securityMiddleware(ctx iris.Context) {
	ctx.Header("X-Content-Type-Options", "nosniff")
	ctx.Header("X-Frame-Options", "DENY")
	ctx.Header("X-XSS-Protection", "1; mode=block")
	ctx.Header("Content-Security-Policy", "default-src 'self'; script-src 'self'")
	ctx.Next()
}

func main() {
	app := iris.New()
	
	app.Use(securityMiddleware)

	app.Get("/gallery", func(ctx iris.Context) {
		imageTitle := ctx.URLParam("title")
		description := ctx.URLParam("desc")
		
		safeTitle := sanitizeInput(imageTitle)
		safeDescription := sanitizeInput(description)
		
		tmpl := template.Must(template.New("gallery").Parse(`<html><body>
		<h1>Image Gallery</h1>
		<div class="image-card">
			<h3>{{.Title}}</h3>
			<p class="description">{{.Description}}</p>
			<img src="/placeholder.jpg" alt="{{.Title}}" />
		</div>
		<style>
		.image-card { border: 1px solid #ddd; padding: 20px; margin: 10px; }
		.description { color: #666; font-style: italic; }
		</style>
		</body></html>`))
		
		data := struct {
			Title       string
			Description string
		}{
			Title:       safeTitle,
			Description: safeDescription,
		}
		
		ctx.Header("Content-Type", "text/html; charset=utf-8")
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		ctx.WriteString(buf.String())
	})

	app.Post("/preferences", func(ctx iris.Context) {
		theme := ctx.FormValue("theme")
		language := ctx.FormValue("language")
		username := ctx.FormValue("username")
		
		safeTheme := sanitizeInput(theme)
		safeLanguage := sanitizeInput(language)
		safeUsername := sanitizeInput(username)
		
		validThemes := []string{"light", "dark", "auto"}
		validLanguages := []string{"en", "es", "fr", "de", "it"}
		
		themeValid := false
		for _, t := range validThemes {
			if safeTheme == t {
				themeValid = true
				break
			}
		}
		if !themeValid {
			safeTheme = "light"
		}
		
		langValid := false
		for _, l := range validLanguages {
			if safeLanguage == l {
				langValid = true
				break
			}
		}
		if !langValid {
			safeLanguage = "en"
		}
		
		if len(safeUsername) > 50 {
			safeUsername = safeUsername[:50]
		}
		
		tmpl := template.Must(template.New("preferences").Parse(`<html><body>
		<h1>Preferences Updated</h1>
		<div class="preferences-summary">
			<p><strong>Username:</strong> {{.Username}}</p>
			<p><strong>Theme:</strong> {{.Theme}}</p>
			<p><strong>Language:</strong> {{.Language}}</p>
		</div>
		<script>
		var settings = {
			user: {{.Username | js}},
			theme: {{.Theme | js}},
			lang: {{.Language | js}}
		};
		console.log("Updated settings for:", settings.user);
		document.title = "Settings - " + settings.user;
		</script>
		<p><a href="/dashboard">Back to Dashboard</a></p>
		</body></html>`))
		
		data := struct {
			Username string
			Theme    string
			Language string
		}{
			Username: safeUsername,
			Theme:    safeTheme,
			Language: safeLanguage,
		}
		
		ctx.Header("Content-Type", "text/html; charset=utf-8")
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		ctx.WriteString(buf.String())
	})

	app.Get("/redirect", func(ctx iris.Context) {
		destination := ctx.URLParam("to")
		message := ctx.URLParam("msg")
		
		if !validateURL(destination) {
			tmpl := template.Must(template.New("error").Parse(`<html><body>
			<h1>Invalid Redirect</h1>
			<p>The provided URL is not allowed for security reasons.</p>
			<a href="/">Return to home</a>
			</body></html>`))
			
			var buf strings.Builder
			tmpl.Execute(&buf, nil)
			ctx.Header("Content-Type", "text/html; charset=utf-8")
			ctx.WriteString(buf.String())
			return
		}
		
		safeMessage := sanitizeInput(message)
		
		tmpl := template.Must(template.New("redirect").Parse(`<html><body>
		<h1>Redirecting...</h1>
		<p>{{.Message}}</p>
		<p>Taking you to: <a href="{{.Destination}}">{{.Destination}}</a></p>
		<script>
		setTimeout(function() {
			window.location = {{.Destination | js}};
		}, 2000);
		</script>
		</body></html>`))
		
		data := struct {
			Destination string
			Message     string
		}{
			Destination: destination,
			Message:     safeMessage,
		}
		
		ctx.Header("Content-Type", "text/html; charset=utf-8")
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		ctx.WriteString(buf.String())
	})

	app.Listen(":8080")
}