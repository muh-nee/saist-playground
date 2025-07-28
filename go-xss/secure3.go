package main

import (
	"html"
	"html/template"
	"net/http"
	"regexp"
	"strings"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
)

func sanitizeInput(input string) string {
	re := regexp.MustCompile(`(?i)<script[^>]*>.*?</script>`)
	cleaned := re.ReplaceAllString(input, "")
	
	dangerousTags := regexp.MustCompile(`(?i)<(iframe|object|embed|link|meta)[^>]*>`)
	cleaned = dangerousTags.ReplaceAllString(cleaned, "")
	
	return html.EscapeString(cleaned)
}

func main() {
	e := echo.New()
	
	e.Use(middleware.Secure())
	e.Use(middleware.CORS())

	e.GET("/feedback", func(c echo.Context) error {
		feedback := c.QueryParam("message")
		
		safeFeedback := sanitizeInput(feedback)
		
		tmpl := template.Must(template.New("feedback").Parse(`<html><body>
		<h1>Feedback Received</h1>
		<div style="border:1px solid #ccc; padding:10px;">
		Your feedback: {{.Feedback}}
		</div>
		<p>Thank you for your input!</p>
		</body></html>`))
		
		data := struct {
			Feedback string
		}{
			Feedback: safeFeedback,
		}
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		return c.HTML(http.StatusOK, buf.String())
	})

	e.GET("/dashboard", func(c echo.Context) error {
		username := c.QueryParam("user")
		role := c.QueryParam("role")
		
		safeUsername := sanitizeInput(username)
		safeRole := sanitizeInput(role)
		
		c.Response().Header().Set("Content-Security-Policy", "default-src 'self'; script-src 'self'")
		
		tmpl := template.Must(template.New("dashboard").Parse(`<html><body>
		<h1>Dashboard</h1>
		<p>Welcome back, {{.Username}}!</p>
		<p>Your role: <span class="role">{{.Role}}</span></p>
		<div id="content">Dashboard content here...</div>
		</body></html>`))
		
		data := struct {
			Username string
			Role     string
		}{
			Username: safeUsername,
			Role:     safeRole,
		}
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		return c.HTML(http.StatusOK, buf.String())
	})

	e.Logger.Fatal(e.Start(":1323"))
}