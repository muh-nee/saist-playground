package main

import (
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
)

func main() {
	userInput := os.Args[1]
	
	filePath := filepath.Join("/tmp", userInput)
	cmd := exec.Command("cat", filePath)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}