package main

import (
	"fmt"
	"os/exec"
	"time"
)

func main() {
	// No user input - all values are hardcoded
	predefinedCommands := [][]string{
		{"whoami"},
		{"date"},
		{"uptime"},
		{"df", "-h"},
		{"free", "-h"},
	}
	
	fmt.Println("System Information Report:")
	fmt.Println("========================")
	
	for i, cmdArgs := range predefinedCommands {
		fmt.Printf("\n%d. Running: %v\n", i+1, cmdArgs)
		
		cmd := exec.Command(cmdArgs[0], cmdArgs[1:]...)
		output, err := cmd.Output()
		if err != nil {
			fmt.Printf("Error: %v\n", err)
			continue
		}
		
		fmt.Printf("%s\n", output)
		
		// Brief pause between commands
		time.Sleep(100 * time.Millisecond)
	}
	
	fmt.Println("Report completed.")
}