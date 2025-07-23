package main

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	userInput := os.Args[1]
	
	bashScript := fmt.Sprintf("#!/bin/bash\necho 'Processing: %s'", userInput)
	cmd := exec.Command("bash", "-c", bashScript)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}