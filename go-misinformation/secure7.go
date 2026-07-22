package main

import (
	"context"
	"log"
	"strings"

	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/llms/openai"
)

func routeTicket(description string) string {
	llm, _ := openai.New(openai.WithModel("gpt-4o"))
	category, _ := llms.GenerateFromSinglePrompt(
		context.Background(),
		llm,
		"Reply with one word: billing, technical, or general.\n\n"+description,
		llms.WithMaxTokens(10),
	)
	category = strings.TrimSpace(strings.ToLower(category))
	if category != "billing" && category != "technical" && category != "general" {
		category = "general"
	}
	log.Printf("routed ticket to category=%s", category)
	return category
}
