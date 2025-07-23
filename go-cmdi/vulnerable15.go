package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"os/exec"
	
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

type ScriptRequest struct {
	Language string `json:"language"`
	Code     string `json:"code"`
	Args     string `json:"args"`
}

func main() {
	r := chi.NewRouter()
	r.Use(middleware.Logger)
	
	// Vulnerable endpoint - command injection via JSON body
	r.Post("/execute", func(w http.ResponseWriter, r *http.Request) {
		var req ScriptRequest
		if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
			http.Error(w, "Invalid JSON", http.StatusBadRequest)
			return
		}
		
		if req.Language == "" || req.Code == "" {
			http.Error(w, "Missing 'language' or 'code' fields", http.StatusBadRequest)
			return
		}
		
		// Vulnerable: using user input directly in script execution
		var script string
		var cmd *exec.Cmd
		
		switch req.Language {
		case "python":
			script = fmt.Sprintf(`#!/usr/bin/env python3
print("Processing: %s")
%s`, req.Args, req.Code)
			cmd = exec.Command("python3", "-c", script)
			
		case "bash":
			script = fmt.Sprintf(`#!/bin/bash
echo "Processing: %s"
%s`, req.Args, req.Code)
			cmd = exec.Command("bash", "-c", script)
			
		case "node":
			script = fmt.Sprintf(`console.log("Processing: %s");
%s`, req.Args, req.Code)
			cmd = exec.Command("node", "-e", script)
			
		default:
			http.Error(w, "Unsupported language", http.StatusBadRequest)
			return
		}
		
		output, err := cmd.Output()
		
		response := map[string]interface{}{
			"language": req.Language,
			"code":     req.Code,
			"args":     req.Args,
			"script":   script,
			"result":   string(output),
		}
		
		if err != nil {
			response["error"] = fmt.Sprintf("Execution failed: %v", err)
			w.WriteHeader(http.StatusInternalServerError)
		}
		
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	})
	
	// Additional vulnerable endpoint for file processing
	r.Post("/process-file", func(w http.ResponseWriter, r *http.Request) {
		var req struct {
			Filename  string `json:"filename"`
			Operation string `json:"operation"`
		}
		
		if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
			http.Error(w, "Invalid JSON", http.StatusBadRequest)
			return
		}
		
		// Vulnerable: file operations with user input
		script := fmt.Sprintf(`#!/usr/bin/env python3
import os
filename = "%s"
operation = "%s"
print(f"Processing {filename} with {operation}")
if operation == "read":
    os.system(f"cat {filename}")
elif operation == "delete":
    os.system(f"rm {filename}")
else:
    os.system(f"ls -la {filename}")`, req.Filename, req.Operation)
		
		cmd := exec.Command("python3", "-c", script)
		output, err := cmd.Output()
		
		response := map[string]interface{}{
			"filename":  req.Filename,
			"operation": req.Operation,
			"script":    script,
			"result":    string(output),
		}
		
		if err != nil {
			response["error"] = fmt.Sprintf("Execution failed: %v", err)
			w.WriteHeader(http.StatusInternalServerError)
		}
		
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	})
	
	fmt.Println("Server starting on :8080")
	fmt.Println("Try: curl -X POST -H 'Content-Type: application/json' -d '{\"language\":\"python\",\"code\":\"print(\\\"hello\\\")\",\"args\":\"test\"}' http://localhost:8080/execute")
	http.ListenAndServe(":8080", r)
}