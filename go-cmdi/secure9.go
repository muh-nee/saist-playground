package main

import (
	"fmt"
	"io/fs"
	"os"
	"path/filepath"
	"regexp"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: secure9 <directory>")
		return
	}
	
	userInput := os.Args[1]
	
	// Validate directory name with regex
	validDir := regexp.MustCompile(`^[a-zA-Z0-9._/-]+$`)
	if !validDir.MatchString(userInput) {
		fmt.Printf("Error: Invalid directory name format\n")
		return
	}
	
	// Use Go's built-in filepath.Walk instead of exec command
	fmt.Printf("Listing files in directory: %s\n", userInput)
	fmt.Println("================================")
	
	err := filepath.WalkDir(userInput, func(path string, d fs.DirEntry, err error) error {
		if err != nil {
			return err
		}
		
		info, err := d.Info()
		if err != nil {
			return err
		}
		
		// Format output similar to ls -la
		fmt.Printf("%s %8d %s %s\n", 
			info.Mode(), 
			info.Size(), 
			info.ModTime().Format("Jan 02 15:04"), 
			path)
		
		return nil
	})
	
	if err != nil {
		fmt.Printf("Error reading directory: %v\n", err)
		return
	}
	
	fmt.Println("Directory listing completed using Go native functions.")
}