package main

import (
	"bytes"
	"fmt"
	"net/http"
	"os/exec"
	"text/template"
	
	"github.com/labstack/echo/v4"
)

func main() {
	e := echo.New()
	
	// Vulnerable endpoint - command injection via form data
	e.POST("/template", func(c echo.Context) error {
		// Get template content from form data
		templateContent := c.FormValue("template")
		name := c.FormValue("name")
		
		if templateContent == "" || name == "" {
			return c.JSON(http.StatusBadRequest, map[string]string{
				"error": "Missing 'template' or 'name' form fields",
			})
		}
		
		// Vulnerable: using user input directly in template and command
		tmpl := fmt.Sprintf("echo '%s'", templateContent)
		t, err := template.New("cmd").Parse(tmpl)
		if err != nil {
			return c.JSON(http.StatusBadRequest, map[string]string{
				"error": fmt.Sprintf("Template parse error: %v", err),
			})
		}
		
		var buf bytes.Buffer
		t.Execute(&buf, name)
		
		// Execute the templated command
		cmd := exec.Command("sh", "-c", buf.String())
		output, err := cmd.Output()
		
		if err != nil {
			return c.JSON(http.StatusInternalServerError, map[string]string{
				"error": fmt.Sprintf("Command failed: %v", err),
			})
		}
		
		return c.JSON(http.StatusOK, map[string]interface{}{
			"template": templateContent,
			"name":     name,
			"command":  buf.String(),
			"result":   string(output),
		})
	})
	
	fmt.Println("Server starting on :8080")
	fmt.Println("Try: curl -X POST -d 'template=Hello {{.}}&name=world' http://localhost:8080/template")
	e.Logger.Fatal(e.Start(":8080"))
}