package main

import (
	"fmt"

	"github.com/gofiber/fiber/v2"
)

func main() {
	app := fiber.New()

	app.Get("/comment", func(c *fiber.Ctx) error {
		comment := c.Query("text")
		html := fmt.Sprintf(`<html><body>
		<h2>Your Comment</h2>
		<div class="comment">%s</div>
		<p><a href="/">Back to home</a></p>
		</body></html>`, comment)
		
		c.Set("Content-Type", "text/html")
		return c.SendString(html)
	})

	app.Get("/welcome", func(c *fiber.Ctx) error {
		name := c.Query("name")
		html := fmt.Sprintf(`<html><body>
		<h1>Welcome Page</h1>
		<script>
		var userName = "%s";
		document.write("<p>Hello " + userName + "!</p>");
		</script>
		</body></html>`, name)
		
		c.Set("Content-Type", "text/html")
		return c.SendString(html)
	})

	app.Listen(":3000")
}