package main

import (
	"fmt"
	"net/http"

	"example.com/internal/aiagent"
)

func handleCustomAgent(w http.ResponseWriter, r *http.Request) {
	instructions := r.FormValue("instructions")
	userMessage := r.FormValue("message")

	agent := aiagent.New(aiagent.Config{
		SystemPrompt: instructions,
		Model:        "gpt-4o",
	})

	result, err := agent.Run(r.Context(), userMessage)
	if err != nil {
		http.Error(w, "failed", http.StatusInternalServerError)
		return
	}
	fmt.Fprintln(w, result)
}

func main() {
	http.HandleFunc("/agent", handleCustomAgent)
	http.ListenAndServe(":8080", nil)
}
