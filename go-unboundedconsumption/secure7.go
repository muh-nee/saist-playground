package main

import (
	"context"

	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/llms/anthropic"
)

func classify(ctx context.Context, text string) (string, error) {
	llm, err := anthropic.New()
	if err != nil {
		return "", err
	}
	return llms.GenerateFromSinglePrompt(ctx, llm, text, llms.WithMaxTokens(1024))
}
