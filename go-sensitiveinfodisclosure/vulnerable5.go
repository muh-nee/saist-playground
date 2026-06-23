package main

import (
	"context"
	"fmt"
	"os"

	"github.com/tmc/langchaingo/llms/openai"
)

func diagnoseAPIFailure(ctx context.Context, service string) (string, error) {
	llm, _ := openai.New()
	paymentKey := os.Getenv("PAYMENT_API_KEY")
	return llm.Call(ctx, fmt.Sprintf("Auth failures on %s. API key in use: %s", service, paymentKey))
}

func main() {
	result, _ := diagnoseAPIFailure(context.Background(), "payments-service")
	fmt.Println(result)
}
