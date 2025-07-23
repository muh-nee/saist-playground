package main

import (
	"fmt"
	"os"
	"os/exec"
	"runtime"
)

func main() {
	userInput := os.Args[1]
	
	if runtime.GOOS == "windows" {
		cmd := exec.Command("cmd.exe", "/C", "dir "+userInput)
		output, err := cmd.Output()
		if err != nil {
			fmt.Printf("Error: %v\n", err)
			return
		}
		fmt.Printf("Output: %s\n", output)
	} else {
		cmd := exec.Command("sh", "-c", "ls "+userInput)
		output, err := cmd.Output()
		if err != nil {
			fmt.Printf("Error: %v\n", err)
			return
		}
		fmt.Printf("Output: %s\n", output)
	}
}