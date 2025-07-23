package main

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: secure1 <directory>")
		return
	}
	
	userInput := os.Args[1]
	
	// Whitelist validation - only allow specific safe directories
	allowedDirs := map[string]bool{
		"/tmp":     true,
		"/home":    true,
		"/var/log": true,
		".":        true,
		"..":       true,
	}
	
	if !allowedDirs[userInput] {
		fmt.Printf("Error: Directory '%s' is not allowed\n", userInput)
		return
	}
	
	cmd := exec.Command("ls", "-la", userInput)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}