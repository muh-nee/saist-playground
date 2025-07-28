package main

import (
	"fmt"
	"net/http"

	"github.com/gobuffalo/buffalo"
	"github.com/gobuffalo/buffalo/render"
)

func main() {
	app := buffalo.New(buffalo.Options{
		Env: "development",
	})

	app.GET("/posts", func(c buffalo.Context) error {
		title := c.Param("title")
		author := c.Param("author")
		
		html := fmt.Sprintf(`<html><body>
		<h1>Blog Post</h1>
		<div class="post-header">
			<h2>%s</h2>
			<p>By: <span class="author">%s</span></p>
		</div>
		<div class="post-content">
			<p>This is a sample blog post content.</p>
		</div>
		</body></html>`, title, author)
		
		c.Response().Header().Set("Content-Type", "text/html")
		return c.Render(http.StatusOK, render.String(html))
	})

	app.POST("/contact", func(c buffalo.Context) error {
		name := c.Param("name")
		email := c.Param("email")
		subject := c.Param("subject")
		message := c.Param("message")
		
		html := fmt.Sprintf(`<html><body>
		<h1>Contact Form Received</h1>
		<div class="contact-summary">
			<p><strong>Name:</strong> %s</p>
			<p><strong>Email:</strong> %s</p>
			<p><strong>Subject:</strong> %s</p>
			<p><strong>Message:</strong></p>
			<div class="message-box" style="border: 1px solid #ccc; padding: 10px;">
				%s
			</div>
		</div>
		<script>
		alert("Thank you %s, we received your message!");
		</script>
		</body></html>`, name, email, subject, message, name)
		
		c.Response().Header().Set("Content-Type", "text/html")
		return c.Render(http.StatusOK, render.String(html))
	})

	app.Serve()
}