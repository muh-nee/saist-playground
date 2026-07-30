package main

import (
	"os/exec"
)

func run(userInput string) {
	// Command injection: user input passed straight to a shell.
	exec.Command("sh", "-c", "echo "+userInput).Run()
}
