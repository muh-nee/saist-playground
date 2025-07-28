package main

import (
	"html"
	"html/template"
	"regexp"
	"strings"

	"github.com/gofiber/fiber/v2"
)

func validateInput(input string) string {
	re := regexp.MustCompile(`(?i)<script[^>]*>.*?</script>`)
	cleaned := re.ReplaceAllString(input, "")
	
	return html.EscapeString(cleaned)
}

func main() {
	app := fiber.New()

	app.Get("/comment", func(c *fiber.Ctx) error {
		comment := c.Query("text")
		
		safeComment := validateInput(comment)
		
		tmpl := template.Must(template.New("comment").Parse(`<html><body>
		<h2>Your Comment</h2>
		<div class="comment">{{.Comment}}</div>
		<p><a href="/">Back to home</a></p>
		</body></html>`))
		
		data := struct {
			Comment string
		}{
			Comment: safeComment,
		}
		
		c.Set("Content-Type", "text/html")
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		return c.SendString(buf.String())
	})

	app.Get("/welcome", func(c *fiber.Ctx) error {
		name := c.Query("name")
		
		safeName := validateInput(name)
		
		tmpl := template.Must(template.New("welcome").Parse(`<html><body>
		<h1>Welcome Page</h1>
		<div id="welcome-message"></div>
		<script>
		var userName = {{.Name | js}};
		document.getElementById('welcome-message').textContent = "Hello " + userName + "!";
		</script>
		</body></html>`))
		
		data := struct {
			Name string
		}{
			Name: safeName,
		}
		
		c.Set("Content-Type", "text/html")
		
		var buf strings.Builder
		tmpl.Execute(&buf, data)
		return c.SendString(buf.String())
	})

	app.Listen(":3000")
}