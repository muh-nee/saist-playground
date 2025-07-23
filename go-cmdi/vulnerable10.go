package main

import (
	"fmt"
	"os/exec"
	
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	
	// Vulnerable endpoint - command injection via query parameter
	r.GET("/search", func(c *gin.Context) {
		// Get search term from query parameter
		searchTerm := c.Query("q")
		
		if searchTerm == "" {
			c.JSON(400, gin.H{"error": "Missing search parameter 'q'"})
			return
		}
		
		// Vulnerable: directly using user input in command
		sqlQuery := fmt.Sprintf("SELECT * FROM users WHERE username='%s'", searchTerm)
		cmd := exec.Command("sqlite3", "database.db", sqlQuery)
		output, err := cmd.Output()
		
		if err != nil {
			c.JSON(500, gin.H{"error": fmt.Sprintf("Command failed: %v", err)})
			return
		}
		
		c.JSON(200, gin.H{
			"query":  searchTerm,
			"result": string(output),
		})
	})
	
	fmt.Println("Server starting on :8080")
	fmt.Println("Try: curl 'http://localhost:8080/search?q=admin'")
	r.Run(":8080")
}