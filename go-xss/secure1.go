package main

import (
	"html"
	"html/template"
	"net/http"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()

	r.GET("/search", func(c *gin.Context) {
		query := c.Query("q")
		
		tmpl := template.Must(template.New("search").Parse(`<html><body>
		<h1>Search Results</h1>
		<p>You searched for: <strong>{{.Query}}</strong></p>
		<p>No results found.</p>
		</body></html>`))
		
		data := struct {
			Query string
		}{
			Query: query,
		}
		
		c.Header("Content-Type", "text/html")
		tmpl.Execute(c.Writer, data)
	})

	r.GET("/profile", func(c *gin.Context) {
		username := c.Query("user")
		if username == "" {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Username parameter is required"})
			return
		}
		
		escapedUsername := html.EscapeString(username)
		
		tmpl := template.Must(template.New("profile").Parse(`<html><body>
		<h1>User Profile</h1>
		<p>Welcome, {{.Username}}!</p>
		</body></html>`))
		
		data := struct {
			Username string
		}{
			Username: escapedUsername,
		}
		
		c.Header("Content-Type", "text/html")
		tmpl.Execute(c.Writer, data)
	})

	r.Run(":8080")
}