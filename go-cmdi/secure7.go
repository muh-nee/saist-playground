package main

import (
	"fmt"
	"os"
	"os/exec"
	"regexp"
	"strings"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: secure7 <message>")
		return
	}
	
	userInput := os.Args[1]
	
	// Remove dangerous characters
	dangerousChars := regexp.MustCompile(`[;&|<>$(){}[\]\\'"` + "`" + `]`)
	if dangerousChars.MatchString(userInput) {
		fmt.Printf("Error: Input contains dangerous characters\n")
		return
	}
	
	// Additional escaping by removing spaces and special sequences
	safeInput := strings.ReplaceAll(userInput, " ", "_")
	safeInput = strings.ReplaceAll(safeInput, "\n", "")
	safeInput = strings.ReplaceAll(safeInput, "\r", "")
	safeInput = strings.ReplaceAll(safeInput, "\t", "")
	
	// Length limit
	if len(safeInput) > 30 {
		fmt.Printf("Error: Message too long (max 30 characters after sanitization)\n")
		return
	}
	
	// Use exec.Command with separate arguments (not shell)
	cmd := exec.Command("echo", "Message:", safeInput)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}