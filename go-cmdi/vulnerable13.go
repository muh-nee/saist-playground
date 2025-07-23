package main

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"os/exec"
)

type ProcessRequest struct {
	ProcessName string `json:"process_name"`
	Options     string `json:"options"`
}

func main() {
	// Vulnerable endpoint - command injection via POST body
	http.HandleFunc("/processes", func(w http.ResponseWriter, r *http.Request) {
		if r.Method != http.MethodPost {
			http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
			return
		}
		
		body, err := io.ReadAll(r.Body)
		if err != nil {
			http.Error(w, "Failed to read request body", http.StatusBadRequest)
			return
		}
		defer r.Body.Close()
		
		var req ProcessRequest
		if err := json.Unmarshal(body, &req); err != nil {
			http.Error(w, "Invalid JSON", http.StatusBadRequest)
			return
		}
		
		if req.ProcessName == "" {
			http.Error(w, "Missing process_name field", http.StatusBadRequest)
			return
		}
		
		// Vulnerable: using user input directly in command
		var sysCmd string
		if req.Options != "" {
			sysCmd = fmt.Sprintf("ps %s | grep %s", req.Options, req.ProcessName)
		} else {
			sysCmd = fmt.Sprintf("ps aux | grep %s", req.ProcessName)
		}
		
		cmd := exec.Command("sh", "-c", sysCmd)
		output, err := cmd.Output()
		
		if err != nil {
			response := map[string]interface{}{
				"error":   fmt.Sprintf("Command failed: %v", err),
				"command": sysCmd,
			}
			w.Header().Set("Content-Type", "application/json")
			w.WriteHeader(http.StatusInternalServerError)
			json.NewEncoder(w).Encode(response)
			return
		}
		
		response := map[string]interface{}{
			"process_name": req.ProcessName,
			"options":      req.Options,
			"command":      sysCmd,
			"result":       string(output),
		}
		
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	})
	
	fmt.Println("Server starting on :8080")
	fmt.Println("Try: curl -X POST -H 'Content-Type: application/json' -d '{\"process_name\":\"nginx\"}' http://localhost:8080/processes")
	http.ListenAndServe(":8080", nil)
}