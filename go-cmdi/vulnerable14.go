package main

import (
	"fmt"
	"net/http"
	"os/exec"
	
	"github.com/gorilla/mux"
)

func main() {
	r := mux.NewRouter()
	
	// Vulnerable endpoint - command injection via path variable
	r.HandleFunc("/backup/{filepath:.*}", func(w http.ResponseWriter, r *http.Request) {
		vars := mux.Vars(r)
		filepath := vars["filepath"]
		
		if filepath == "" {
			http.Error(w, "Missing filepath parameter", http.StatusBadRequest)
			return
		}
		
		// Get optional backup name from query parameter
		backupName := r.URL.Query().Get("name")
		if backupName == "" {
			backupName = "backup.tar.gz"
		}
		
		// Vulnerable: using user input directly in tar command
		fileCmd := fmt.Sprintf("tar -czf %s %s", backupName, filepath)
		cmd := exec.Command("sh", "-c", fileCmd)
		output, err := cmd.Output()
		
		if err != nil {
			w.Header().Set("Content-Type", "application/json")
			w.WriteHeader(http.StatusInternalServerError)
			fmt.Fprintf(w, `{"error": "Command failed: %v", "command": "%s"}`, err, fileCmd)
			return
		}
		
		w.Header().Set("Content-Type", "application/json")
		fmt.Fprintf(w, `{"filepath": "%s", "backup_name": "%s", "command": "%s", "result": "%s"}`, 
			filepath, backupName, fileCmd, string(output))
	}).Methods("POST")
	
	// Additional vulnerable endpoint for file operations
	r.HandleFunc("/compress/{algorithm}/{file:.*}", func(w http.ResponseWriter, r *http.Request) {
		vars := mux.Vars(r)
		algorithm := vars["algorithm"]
		file := vars["file"]
		
		if algorithm == "" || file == "" {
			http.Error(w, "Missing algorithm or file parameter", http.StatusBadRequest)
			return
		}
		
		// Vulnerable: constructing command with user input
		var compressCmd string
		switch algorithm {
		case "gzip":
			compressCmd = fmt.Sprintf("gzip -c %s > %s.gz", file, file)
		case "bzip2":
			compressCmd = fmt.Sprintf("bzip2 -c %s > %s.bz2", file, file)
		default:
			compressCmd = fmt.Sprintf("tar -czf %s.tar.gz %s", file, file)
		}
		
		cmd := exec.Command("sh", "-c", compressCmd)
		output, err := cmd.Output()
		
		if err != nil {
			w.Header().Set("Content-Type", "application/json")
			w.WriteHeader(http.StatusInternalServerError)
			fmt.Fprintf(w, `{"error": "Command failed: %v", "command": "%s"}`, err, compressCmd)
			return
		}
		
		w.Header().Set("Content-Type", "application/json")
		fmt.Fprintf(w, `{"algorithm": "%s", "file": "%s", "command": "%s", "result": "%s"}`, 
			algorithm, file, compressCmd, string(output))
	}).Methods("POST")
	
	fmt.Println("Server starting on :8080")
	fmt.Println("Try: curl -X POST http://localhost:8080/backup/myfile.txt")
	fmt.Println("Try: curl -X POST http://localhost:8080/compress/gzip/test.txt")
	http.ListenAndServe(":8080", r)
}