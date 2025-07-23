package main

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("Usage: secure8 <command_id>")
		fmt.Println("Available commands:")
		fmt.Println("  1 - Show current directory")
		fmt.Println("  2 - Show disk usage")  
		fmt.Println("  3 - Show memory usage")
		fmt.Println("  4 - Show network interfaces")
		fmt.Println("  5 - Show running processes")
		return
	}
	
	commandID := os.Args[1]
	
	// Predefined commands mapped to safe IDs
	commands := map[string][]string{
		"1": {"pwd"},
		"2": {"df", "-h"},
		"3": {"free", "-m"},
		"4": {"ifconfig"},
		"5": {"ps", "aux"},
	}
	
	cmdArgs, exists := commands[commandID]
	if !exists {
		fmt.Printf("Error: Invalid command ID '%s'. Use 1-5 only.\n", commandID)
		return
	}
	
	fmt.Printf("Executing predefined command: %v\n", cmdArgs)
	
	cmd := exec.Command(cmdArgs[0], cmdArgs[1:]...)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		return
	}
	
	fmt.Printf("Output: %s\n", output)
}