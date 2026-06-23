package main

import (
	"context"
	"fmt"
	"os"

	"github.com/tmc/langchaingo/chains"
	langchaingopenai "github.com/tmc/langchaingo/llms/openai"
	"github.com/tmc/langchaingo/prompts"
)

func investigateOAuthFailure(ctx context.Context, provider string) (string, error) {
	llm, _ := langchaingopenai.New()
	chain := chains.NewLLMChain(llm, prompts.NewPromptTemplate("{{.input}}", []string{"input"}))
	oauthSecret := os.Getenv("OAUTH_CLIENT_SECRET")
	return chains.Run(ctx, chain, fmt.Sprintf("Investigate OAuth failure for %s. Client secret: %s", provider, oauthSecret))
}

func main() {
	result, _ := investigateOAuthFailure(context.Background(), "github")
	fmt.Println(result)
}
