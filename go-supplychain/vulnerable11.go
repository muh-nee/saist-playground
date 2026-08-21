package main

import (
	"context"
	"net/http"
	"os/exec"
	"strings"

	"github.com/openai/openai-go"
	"github.com/openai/openai-go/option"
)

func installSuggestedModule(w http.ResponseWriter, r *http.Request) {
	client := openai.NewClient(option.WithAPIKey("sk-..."))
	task := r.URL.Query().Get("task")
	chat, _ := client.Chat.Completions.New(context.Background(), openai.ChatCompletionNewParams{
		Model: openai.F(openai.ChatModelGPT4o),
		Messages: openai.F([]openai.ChatCompletionMessageParamUnion{
			openai.UserMessage("What Go module should I use for: " + task + "? Reply with only the module path."),
		}),
	})
	moduleName := strings.TrimSpace(chat.Choices[0].Message.Content)
	exec.Command("go", "get", moduleName).Run()
	w.Write([]byte("installed: " + moduleName))
}
