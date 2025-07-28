package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()

	r.GET("/search", func(c *gin.Context) {
		query := c.Query("q")
		html := `<html><body>
		<h1>Search Results</h1>
		<p>You searched for: ` + query + `</p>
		<p>No results found.</p>
		</body></html>`
		
		c.Header("Content-Type", "text/html")
		c.String(http.StatusOK, html)
	})

	r.GET("/profile", func(c *gin.Context) {
		username := c.Query("user")
		if username == "" {
			c.Header("Content-Type", "text/html")
			c.String(http.StatusBadRequest, `<div style="color:red">Error: Please provide a username parameter like ?user=` + c.Query("example") + `</div>`)
			return
		}
		c.JSON(http.StatusOK, gin.H{"message": "Profile for " + username})
	})

	r.Run(":8080")
}