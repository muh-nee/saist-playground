package main

import (
	"fmt"
	"os"
	"os/exec"
	"strings"
)

func main() {
	userInput := os.Args[1]
	
	args := strings.Split(userInput, " ")
	cmd := exec.Command("find", args...)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}