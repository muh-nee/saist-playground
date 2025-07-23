package main

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	userInput := os.Args[1]
	
	cmd := exec.Command("ls", userInput)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}