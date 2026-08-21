package main

import (
	"context"
	"net/http"
	"os/exec"
	"strings"

	"github.com/openai/openai-go"
	"github.com/openai/openai-go/option"
)

func setupDependencies(w http.ResponseWriter, r *http.Request) {
	client := openai.NewClient(option.WithAPIKey("sk-..."))
	feature := r.URL.Query().Get("feature")
	chat, _ := client.Chat.Completions.New(context.Background(), openai.ChatCompletionNewParams{
		Model: openai.F(openai.ChatModelGPT4o),
		Messages: openai.F([]openai.ChatCompletionMessageParamUnion{
			openai.UserMessage("List Go modules needed for: " + feature + ". One module path per line."),
		}),
	})
	for _, line := range strings.Split(chat.Choices[0].Message.Content, "\n") {
		module := strings.TrimSpace(line)
		if module != "" {
			exec.Command("go", "get", module).Run()
		}
	}
	w.WriteHeader(http.StatusOK)
}
