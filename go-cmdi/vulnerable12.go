package main

import (
	"fmt"
	"os/exec"
	
	"github.com/gofiber/fiber/v2"
)

func main() {
	app := fiber.New()
	
	// Vulnerable endpoint - command injection via URL parameter
	app.Get("/fetch/:url", func(c *fiber.Ctx) error {
		// Get URL from path parameter
		targetURL := c.Params("url")
		
		if targetURL == "" {
			return c.Status(400).JSON(fiber.Map{
				"error": "Missing URL parameter",
			})
		}
		
		// Vulnerable: directly using user input in curl command
		netCmd := fmt.Sprintf("curl -s http://example.com/?param=%s", targetURL)
		cmd := exec.Command("sh", "-c", netCmd)
		output, err := cmd.Output()
		
		if err != nil {
			return c.Status(500).JSON(fiber.Map{
				"error": fmt.Sprintf("Command failed: %v", err),
			})
		}
		
		return c.JSON(fiber.Map{
			"url":     targetURL,
			"command": netCmd,
			"result":  string(output),
		})
	})
	
	// Additional vulnerable endpoint with query parameters
	app.Get("/proxy", func(c *fiber.Ctx) error {
		host := c.Query("host")
		port := c.Query("port", "80")
		
		if host == "" {
			return c.Status(400).JSON(fiber.Map{
				"error": "Missing 'host' query parameter",
			})
		}
		
		// Vulnerable: using user input in netcat command
		netCmd := fmt.Sprintf("nc -z %s %s", host, port)
		cmd := exec.Command("sh", "-c", netCmd)
		output, err := cmd.Output()
		
		if err != nil {
			return c.Status(500).JSON(fiber.Map{
				"error": fmt.Sprintf("Command failed: %v", err),
			})
		}
		
		return c.JSON(fiber.Map{
			"host":    host,
			"port":    port,
			"command": netCmd,
			"result":  string(output),
		})
	})
	
	fmt.Println("Server starting on :3000")
	fmt.Println("Try: curl http://localhost:3000/fetch/test")
	fmt.Println("Try: curl 'http://localhost:3000/proxy?host=google.com&port=80'")
	app.Listen(":3000")
}