package main

import (
	"fmt"
	"os"
	"os/exec"
	"regexp"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: secure2 <filename>")
		return
	}
	
	userInput := os.Args[1]
	
	// Strict regex validation - only alphanumeric, dots, hyphens, underscores
	validPattern := regexp.MustCompile(`^[a-zA-Z0-9._-]+$`)
	if !validPattern.MatchString(userInput) {
		fmt.Printf("Error: Invalid filename format. Only alphanumeric characters, dots, hyphens, and underscores allowed.\n")
		return
	}
	
	// Additional length check
	if len(userInput) > 50 {
		fmt.Printf("Error: Filename too long (max 50 characters)\n")
		return
	}
	
	cmd := exec.Command("file", userInput)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}