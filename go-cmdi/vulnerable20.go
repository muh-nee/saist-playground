package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"os/exec"
	"strings"
)

// Custom middleware that extracts commands from multiple input sources
func commandExtractorMiddleware(next http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Extract commands from various sources and store in context
		commands := make([]string, 0)
		
		// From query parameters
		if queryCmd := r.URL.Query().Get("cmd"); queryCmd != "" {
			commands = append(commands, queryCmd)
		}
		
		// From headers
		if headerCmd := r.Header.Get("X-Command"); headerCmd != "" {
			commands = append(commands, headerCmd)
		}
		
		// From form data
		if r.Method == "POST" {
			r.ParseForm()
			if formCmd := r.FormValue("command"); formCmd != "" {
				commands = append(commands, formCmd)
			}
		}
		
		// Store in request context (simplified - using custom header)
		if len(commands) > 0 {
			r.Header.Set("X-Extracted-Commands", strings.Join(commands, ";"))
		}
		
		next(w, r)
	}
}

func main() {
	// Vulnerable endpoint - command injection from multiple input sources via custom middleware
	http.HandleFunc("/multi-exec", commandExtractorMiddleware(func(w http.ResponseWriter, r *http.Request) {
		// Get commands extracted by middleware
		commandsStr := r.Header.Get("X-Extracted-Commands")
		if commandsStr == "" {
			http.Error(w, "No commands found in query, header, or form data", http.StatusBadRequest)
			return
		}
		
		commands := strings.Split(commandsStr, ";")
		
		// Get execution mode from various sources
		mode := "sequential"
		if modeQuery := r.URL.Query().Get("mode"); modeQuery != "" {
			mode = modeQuery
		}
		if modeHeader := r.Header.Get("X-Mode"); modeHeader != "" {
			mode = modeHeader
		}
		
		// Get iteration count
		iterations := "1"
		if iterQuery := r.URL.Query().Get("iterations"); iterQuery != "" {
			iterations = iterQuery
		}
		if iterHeader := r.Header.Get("X-Iterations"); iterHeader != "" {
			iterations = iterHeader
		}
		
		results := make([]map[string]interface{}, 0)
		
		for cmdIndex, command := range commands {
			// Vulnerable: using extracted commands in loop-based execution
			var loopCmd string
			
			switch mode {
			case "parallel":
				loopCmd = fmt.Sprintf("for i in $(seq 1 %s); do (%s) & done; wait", iterations, command)
			case "conditional":
				loopCmd = fmt.Sprintf("for i in $(seq 1 %s); do if [ $i -le %s ]; then %s; fi; done", iterations, iterations, command)
			default: // sequential
				loopCmd = fmt.Sprintf("for i in $(seq 1 %s); do echo \"Iteration $i:\" && %s; done", iterations, command)
			}
			
			cmd := exec.Command("bash", "-c", loopCmd)
			output, err := cmd.Output()
			
			result := map[string]interface{}{
				"index":      cmdIndex,
				"command":    command,
				"mode":       mode,
				"iterations": iterations,
				"full_command": loopCmd,
				"result":     string(output),
			}
			
			if err != nil {
				result["error"] = fmt.Sprintf("Command failed: %v", err)
			}
			
			results = append(results, result)
		}
		
		response := map[string]interface{}{
			"total_commands": len(commands),
			"mode":          mode,
			"iterations":    iterations,
			"results":       results,
		}
		
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	}))
	
	// Additional endpoint for pipeline-based execution
	http.HandleFunc("/pipeline", func(w http.ResponseWriter, r *http.Request) {
		// Get pipeline stages from JSON body
		var req struct {
			Stages []string `json:"stages"`
			Input  string   `json:"input"`
			Mode   string   `json:"mode"`
		}
		
		if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
			http.Error(w, "Invalid JSON", http.StatusBadRequest)
			return
		}
		
		if len(req.Stages) == 0 {
			http.Error(w, "No pipeline stages provided", http.StatusBadRequest)
			return
		}
		
		// Vulnerable: constructing pipeline commands with user input
		var pipelineCmd string
		if req.Mode == "loop" && req.Input != "" {
			// Loop over input items
			pipelineCmd = fmt.Sprintf("for item in %s; do", req.Input)
			for i, stage := range req.Stages {
				if i == 0 {
					pipelineCmd += fmt.Sprintf(" echo $item | %s", stage)
				} else {
					pipelineCmd += fmt.Sprintf(" | %s", stage)
				}
			}
			pipelineCmd += "; done"
		} else {
			// Simple pipeline
			pipelineCmd = strings.Join(req.Stages, " | ")
			if req.Input != "" {
				pipelineCmd = fmt.Sprintf("echo '%s' | %s", req.Input, pipelineCmd)
			}
		}
		
		cmd := exec.Command("bash", "-c", pipelineCmd)
		output, err := cmd.Output()
		
		response := map[string]interface{}{
			"stages":   req.Stages,
			"input":    req.Input,
			"mode":     req.Mode,
			"pipeline": pipelineCmd,
			"result":   string(output),
		}
		
		if err != nil {
			response["error"] = fmt.Sprintf("Pipeline failed: %v", err)
			w.WriteHeader(http.StatusInternalServerError)
		}
		
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	})
	
	fmt.Println("Custom middleware server starting on :8080")
	fmt.Println("Try: curl 'http://localhost:8080/multi-exec?cmd=whoami&mode=sequential&iterations=2'")
	fmt.Println("Try: curl -H 'X-Command: ls /tmp' -H 'X-Mode: parallel' http://localhost:8080/multi-exec")
	fmt.Println("Try: curl -X POST -H 'Content-Type: application/json' -d '{\"stages\":[\"grep root\",\"wc -l\"],\"input\":\"/etc/passwd\",\"mode\":\"loop\"}' http://localhost:8080/pipeline")
	
	http.ListenAndServe(":8080", nil)
}