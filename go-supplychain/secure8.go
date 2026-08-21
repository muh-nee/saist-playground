package main

import (
	"context"
	"errors"
	"net/http"
	"os/exec"
	"strings"

	"github.com/openai/openai-go"
	"github.com/openai/openai-go/option"
)

var approvedModules = map[string]bool{
	"github.com/yalue/onnxruntime_go":             true,
	"github.com/orktes/go-torch":                   true,
	"github.com/AdvancedClimateSystems/gonnx":       true,
}

func installApprovedModule(w http.ResponseWriter, r *http.Request) {
	client := openai.NewClient(option.WithAPIKey("sk-..."))
	task := r.URL.Query().Get("task")
	chat, _ := client.Chat.Completions.New(context.Background(), openai.ChatCompletionNewParams{
		Model: openai.F(openai.ChatModelGPT4o),
		Messages: openai.F([]openai.ChatCompletionMessageParamUnion{
			openai.UserMessage("What Go module for: " + task + "? Reply with only the module path."),
		}),
	})
	moduleName := strings.TrimSpace(chat.Choices[0].Message.Content)
	if !approvedModules[moduleName] {
		http.Error(w, errors.New("module not approved").Error(), http.StatusBadRequest)
		return
	}
	exec.Command("go", "get", moduleName).Run()
	w.Write([]byte("installed: " + moduleName))
}
