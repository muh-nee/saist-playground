package main

import (
	"net/http"

	"github.com/labstack/echo/v4"
)

func main() {
	e := echo.New()

	e.GET("/feedback", func(c echo.Context) error {
		feedback := c.QueryParam("message")
		html := `<html><body>
		<h1>Feedback Received</h1>
		<div style="border:1px solid #ccc; padding:10px;">
		Your feedback: ` + feedback + `
		</div>
		<p>Thank you for your input!</p>
		</body></html>`
		
		return c.HTML(http.StatusOK, html)
	})

	e.GET("/dashboard", func(c echo.Context) error {
		username := c.QueryParam("user")
		role := c.QueryParam("role")
		
		html := `<html><body>
		<h1>Dashboard</h1>
		<p>Welcome back, ` + username + `!</p>
		<p>Your role: <span class="role">` + role + `</span></p>
		<div id="content">Dashboard content here...</div>
		</body></html>`
		
		return c.HTML(http.StatusOK, html)
	})

	e.Logger.Fatal(e.Start(":1323"))
}