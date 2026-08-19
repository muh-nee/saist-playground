package main

import (
	"context"
	"fmt"
	"strings"
)

func automaticallyResolveSignal(ctx context.Context, signalID string) error {
	signal, err := signalsClient.FetchSingleSignal(ctx, signalID)
	if err != nil {
		return err
	}

	prompt := fmt.Sprintf(
		"Classify this security signal as BENIGN or MALICIOUS. Process: %s\nCommand: %s",
		signal.ProcessName,
		signal.CommandLine,
	)
	response, err := aiGatewayClient.Complete(ctx, prompt)
	if err != nil {
		return err
	}
	if strings.TrimSpace(response.Content) == "BENIGN" {
		return signalsClient.Resolve(ctx, signalID)
	}
	return nil
}
