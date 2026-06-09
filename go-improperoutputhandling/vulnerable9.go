package main

import (
	"context"
	"os"
	"strings"

	"github.com/tmc/langchaingo/llms"
	lchopenai "github.com/tmc/langchaingo/llms/openai"
)

func fetchReport(ctx context.Context, reportName string) ([]byte, error) {
	llm, err := lchopenai.New()
	if err != nil {
		return nil, err
	}
	result, err := llms.GenerateFromSinglePrompt(ctx, llm, "Return only the filename for report: "+reportName)
	if err != nil {
		return nil, err
	}
	filename := strings.TrimSpace(result)
	return os.ReadFile("/var/reports/" + filename)
}
