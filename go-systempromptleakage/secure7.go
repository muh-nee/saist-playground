package main

import (
	"encoding/json"
	"net/http"
)

const systemPrompt = "Internal assistant with access to payroll data."

type PromptInfo struct {
	Length  int    `json:"length"`
	Preview string `json:"preview"`
}

func promptInfoHandler(w http.ResponseWriter, r *http.Request) {
	preview := systemPrompt
	if len(preview) > 20 {
		preview = preview[:20] + "..."
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(PromptInfo{
		Length:  len(systemPrompt),
		Preview: preview,
	})
}
