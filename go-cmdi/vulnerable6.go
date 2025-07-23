package main

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	userInput := os.Args[1]
	
	pipeCmd := fmt.Sprintf("cat /etc/passwd | grep %s", userInput)
	cmd := exec.Command("sh", "-c", pipeCmd)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}