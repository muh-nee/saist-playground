package main

import (
	"fmt"
	"os"
	"os/exec"
	"strconv"
	"strings"
)

type SafeConfig struct {
	MaxLines int
	MaxSize  int64
	AllowedExt []string
}

func main() {
	if len(os.Args) < 3 {
		fmt.Println("Usage: secure10 <lines> <filename>")
		return
	}
	
	linesArg := os.Args[1]
	filename := os.Args[2]
	
	config := SafeConfig{
		MaxLines: 100,
		MaxSize: 1024 * 1024, // 1MB
		AllowedExt: []string{".txt", ".log", ".md", ".go"},
	}
	
	// Strict type validation for lines parameter
	lines, err := strconv.Atoi(linesArg)
	if err != nil {
		fmt.Printf("Error: Lines parameter must be a valid integer\n")
		return
	}
	
	if lines < 1 || lines > config.MaxLines {
		fmt.Printf("Error: Lines must be between 1 and %d\n", config.MaxLines)
		return
	}
	
	// Validate filename extension
	validExt := false
	for _, ext := range config.AllowedExt {
		if strings.HasSuffix(strings.ToLower(filename), ext) {
			validExt = true
			break
		}
	}
	
	if !validExt {
		fmt.Printf("Error: File extension not allowed. Allowed: %v\n", config.AllowedExt)
		return
	}
	
	// Check file size before processing
	fileInfo, err := os.Stat(filename)
	if err != nil {
		fmt.Printf("Error: Cannot access file: %v\n", err)
		return
	}
	
	if fileInfo.Size() > config.MaxSize {
		fmt.Printf("Error: File too large (max %d bytes)\n", config.MaxSize)
		return
	}
	
	// Use validated parameters separately
	cmd := exec.Command("head", "-n", strconv.Itoa(lines), filename)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("First %d lines of %s:\n", lines, filename)
	fmt.Printf("Output: %s\n", output)
}