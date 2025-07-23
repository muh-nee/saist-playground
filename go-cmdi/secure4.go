package main

import (
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"strings"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: secure4 <filepath>")
		return
	}
	
	userInput := os.Args[1]
	
	// Clean and validate the path
	cleanPath := filepath.Clean(userInput)
	
	// Prevent directory traversal attacks
	if strings.Contains(cleanPath, "..") {
		fmt.Printf("Error: Directory traversal not allowed\n")
		return
	}
	
	// Ensure path is absolute and within allowed directory
	absPath, err := filepath.Abs(cleanPath)
	if err != nil {
		fmt.Printf("Error: Invalid path: %v\n", err)
		return
	}
	
	// Restrict to /tmp directory only
	allowedPrefix := "/tmp"
	if !strings.HasPrefix(absPath, allowedPrefix) {
		fmt.Printf("Error: Access denied. Path must be within /tmp directory\n")
		return
	}
	
	cmd := exec.Command("stat", absPath)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}