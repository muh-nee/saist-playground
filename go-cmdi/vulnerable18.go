package main

import (
	"fmt"
	"mime/multipart"
	"net/http"
	"os/exec"
	"strconv"
	
	"github.com/kataras/iris/v12"
)

func main() {
	app := iris.New()
	
	// Vulnerable endpoint - command injection via multipart form
	app.Post("/upload-process", func(ctx iris.Context) {
		// Parse multipart form
		err := ctx.Request().ParseMultipartForm(10 << 20) // 10MB max
		if err != nil {
			ctx.StatusCode(iris.StatusBadRequest)
			ctx.JSON(iris.Map{"error": "Failed to parse multipart form"})
			return
		}
		
		form := ctx.Request().MultipartForm
		
		// Get command parameters from form fields
		command := ""
		if commands, ok := form.Value["command"]; ok && len(commands) > 0 {
			command = commands[0]
		}
		
		if command == "" {
			ctx.StatusCode(iris.StatusBadRequest)
			ctx.JSON(iris.Map{"error": "Missing 'command' field"})
			return
		}
		
		// Get delay from form
		delay := "5"
		if delays, ok := form.Value["delay"]; ok && len(delays) > 0 {
			delay = delays[0]
		}
		
		// Get background mode from form
		background := false
		if bg, ok := form.Value["background"]; ok && len(bg) > 0 {
			background = bg[0] == "true"
		}
		
		// Process uploaded files if any
		fileInfo := ""
		if files, ok := form.File["file"]; ok && len(files) > 0 {
			file := files[0]
			fileInfo = fmt.Sprintf("Processing file: %s", file.Filename)
		}
		
		// Vulnerable: using form data directly in command construction
		var bgCmd string
		if background {
			bgCmd = fmt.Sprintf("sleep %s && echo '%s - %s' &", delay, command, fileInfo)
		} else {
			bgCmd = fmt.Sprintf("sleep %s && echo '%s - %s'", delay, command, fileInfo)
		}
		
		cmd := exec.Command("bash", "-c", bgCmd)
		output, err := cmd.Output()
		
		response := iris.Map{
			"command":    command,
			"delay":      delay,
			"background": background,
			"file_info":  fileInfo,
			"full_command": bgCmd,
			"result":     string(output),
		}
		
		if err != nil {
			response["error"] = fmt.Sprintf("Command failed: %v", err)
			ctx.StatusCode(iris.StatusInternalServerError)
		}
		
		ctx.JSON(response)
	})
	
	// Additional vulnerable endpoint for batch processing
	app.Post("/batch-process", func(ctx iris.Context) {
		err := ctx.Request().ParseMultipartForm(10 << 20)
		if err != nil {
			ctx.StatusCode(iris.StatusBadRequest)
			ctx.JSON(iris.Map{"error": "Failed to parse multipart form"})
			return
		}
		
		form := ctx.Request().MultipartForm
		
		// Get batch operations from form
		operations, ok := form.Value["operations"]
		if !ok || len(operations) == 0 {
			ctx.StatusCode(iris.StatusBadRequest)
			ctx.JSON(iris.Map{"error": "Missing 'operations' field"})
			return
		}
		
		// Get parallel execution flag
		parallel := false
		if p, ok := form.Value["parallel"]; ok && len(p) > 0 {
			parallel = p[0] == "true"
		}
		
		results := make([]map[string]interface{}, 0)
		
		for i, operation := range operations {
			// Vulnerable: executing user-provided operations
			var batchCmd string
			if parallel {
				batchCmd = fmt.Sprintf("(%s) &", operation)
			} else {
				batchCmd = operation
			}
			
			cmd := exec.Command("bash", "-c", batchCmd)
			output, err := cmd.Output()
			
			result := map[string]interface{}{
				"index":     i,
				"operation": operation,
				"command":   batchCmd,
				"result":    string(output),
			}
			
			if err != nil {
				result["error"] = fmt.Sprintf("Command failed: %v", err)
			}
			
			results = append(results, result)
		}
		
		ctx.JSON(iris.Map{
			"parallel": parallel,
			"count":    len(operations),
			"results":  results,
		})
	})
	
	// Helper function to handle file uploads
	handleFileUpload := func(file *multipart.FileHeader) string {
		return fmt.Sprintf("Uploaded: %s (%d bytes)", file.Filename, file.Size)
	}
	
	// Store the helper function (not used but shows pattern)
	_ = handleFileUpload
	
	fmt.Println("Iris server starting on :8080")
	fmt.Println("Try: curl -X POST -F 'command=hello world' -F 'delay=2' -F 'background=true' http://localhost:8080/upload-process")
	fmt.Println("Try: curl -X POST -F 'operations=ls /tmp' -F 'operations=whoami' -F 'parallel=true' http://localhost:8080/batch-process")
	
	app.Listen(":8080")
}