package main

import (
	"context"

	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/llms/openai"
)

func generate(ctx context.Context, prompt string) (string, error) {
	llm, err := openai.New()
	if err != nil {
		return "", err
	}
	return llms.GenerateFromSinglePrompt(ctx, llm, prompt)
}
