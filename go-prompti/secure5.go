package main

import (
	"context"
	"fmt"
)

type SignalEvent struct {
	ProcessName string
	CommandLine string
}

func generateAdvisorySummary(ctx context.Context, signalID string) (string, error) {
	signal, err := signalsClient.FetchSingleSignal(ctx, signalID)
	if err != nil {
		return "", err
	}

	prompt := fmt.Sprintf(
		"Summarize this event for human review. Process: %s\nCommand: %s",
		signal.ProcessName,
		signal.CommandLine,
	)
	response, err := aiGatewayClient.Complete(ctx, prompt)
	if err != nil {
		return "", err
	}
	return "AI-generated advisory summary; verify against the event:\n" + response.Content, nil
}
