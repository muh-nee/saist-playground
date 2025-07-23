package main

import (
	"fmt"
	"os"
	"os/exec"
	"strconv"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: secure3 <process_id>")
		return
	}
	
	userInput := os.Args[1]
	
	// Validate input is a valid integer (PID)
	pid, err := strconv.Atoi(userInput)
	if err != nil {
		fmt.Printf("Error: Invalid process ID. Must be a number.\n")
		return
	}
	
	// Additional validation for reasonable PID range
	if pid < 1 || pid > 65535 {
		fmt.Printf("Error: Process ID out of valid range (1-65535)\n")
		return
	}
	
	// Use the validated integer directly, not the string
	cmd := exec.Command("ps", "-p", strconv.Itoa(pid))
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}