package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"os/exec"
	"time"
)

func main() {
	// Vulnerable endpoint - command injection via cookie
	http.HandleFunc("/save-log", func(w http.ResponseWriter, r *http.Request) {
		// Get log message from cookie
		cookie, err := r.Cookie("log_message")
		if err != nil {
			http.Error(w, "Missing log_message cookie", http.StatusBadRequest)
			return
		}
		
		logMessage := cookie.Value
		if logMessage == "" {
			http.Error(w, "Empty log message", http.StatusBadRequest)
			return
		}
		
		// Get optional filename from cookie
		filenameCookie, err := r.Cookie("log_filename")
		filename := "/tmp/output.txt"
		if err == nil && filenameCookie.Value != "" {
			filename = filenameCookie.Value
		}
		
		// Vulnerable: using cookie values directly in command with redirection
		redirectCmd := fmt.Sprintf("echo %s > %s", logMessage, filename)
		cmd := exec.Command("sh", "-c", redirectCmd)
		output, err := cmd.Output()
		
		response := map[string]interface{}{
			"message":  logMessage,
			"filename": filename,
			"command":  redirectCmd,
			"result":   string(output),
		}
		
		if err != nil {
			response["error"] = fmt.Sprintf("Command failed: %v", err)
			w.WriteHeader(http.StatusInternalServerError)
		}
		
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	})
	
	// Additional vulnerable endpoint for log processing
	http.HandleFunc("/process-logs", func(w http.ResponseWriter, r *http.Request) {
		// Get processing options from cookies
		actionCookie, err := r.Cookie("action")
		if err != nil {
			http.Error(w, "Missing action cookie", http.StatusBadRequest)
			return
		}
		
		action := actionCookie.Value
		targetCookie, _ := r.Cookie("target")
		target := "/var/log/app.log"
		if targetCookie != nil && targetCookie.Value != "" {
			target = targetCookie.Value
		}
		
		outputCookie, _ := r.Cookie("output")
		output := "/tmp/processed.log"
		if outputCookie != nil && outputCookie.Value != "" {
			output = outputCookie.Value
		}
		
		// Vulnerable: using cookie values in log processing commands
		var processCmd string
		switch action {
		case "filter":
			patternCookie, _ := r.Cookie("pattern")
			pattern := "ERROR"
			if patternCookie != nil {
				pattern = patternCookie.Value
			}
			processCmd = fmt.Sprintf("grep %s %s > %s", pattern, target, output)
		case "compress":
			processCmd = fmt.Sprintf("gzip -c %s > %s.gz", target, output)
		case "rotate":
			timestamp := time.Now().Format("20060102-150405")
			processCmd = fmt.Sprintf("mv %s %s.%s && touch %s", target, target, timestamp, target)
		default:
			processCmd = fmt.Sprintf("tail -n 100 %s > %s", target, output)
		}
		
		cmd := exec.Command("sh", "-c", processCmd)
		cmdOutput, err := cmd.Output()
		
		response := map[string]interface{}{
			"action":  action,
			"target":  target,
			"output":  output,
			"command": processCmd,
			"result":  string(cmdOutput),
		}
		
		if err != nil {
			response["error"] = fmt.Sprintf("Command failed: %v", err)
			w.WriteHeader(http.StatusInternalServerError)
		}
		
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	})
	
	fmt.Println("Server starting on :8080")
	fmt.Println("Try: curl -H 'Cookie: log_message=hello world' http://localhost:8080/save-log")
	fmt.Println("Try: curl -H 'Cookie: action=filter; pattern=test; target=/etc/passwd' http://localhost:8080/process-logs")
	http.ListenAndServe(":8080", nil)
}