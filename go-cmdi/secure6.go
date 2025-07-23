package main

import (
	"fmt"
	"os"
	"os/exec"
	"strings"
	"unicode"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: secure6 <search_term>")
		return
	}
	
	userInput := os.Args[1]
	
	// Strict length validation
	if len(userInput) > 20 {
		fmt.Printf("Error: Search term too long (max 20 characters)\n")
		return
	}
	
	if len(userInput) < 2 {
		fmt.Printf("Error: Search term too short (min 2 characters)\n")
		return
	}
	
	// Character validation - only letters and numbers
	for _, char := range userInput {
		if !unicode.IsLetter(char) && !unicode.IsDigit(char) {
			fmt.Printf("Error: Only letters and numbers allowed in search term\n")
			return
		}
	}
	
	// Convert to lowercase for safety
	safeTerm := strings.ToLower(userInput)
	
	// Use grep with safe options and validated input
	cmd := exec.Command("grep", "-i", safeTerm, "/etc/passwd")
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}