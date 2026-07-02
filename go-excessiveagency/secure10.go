package main

import (
	"context"
	"errors"
	"fmt"

	anthropic "github.com/anthropics/anthropic-sdk-go"
)

// Human-in-the-loop approval: the LLM proposes a refund via the tool, but
// the mutation is only executed after an out-of-band human approves it.
// The tool handler enqueues the request and returns a ticket ID; a separate
// approval channel (not the LLM) authorizes the actual refund.

type refundRequest struct {
	OrderID string
	Amount  int64
}

var pendingRefunds = map[string]refundRequest{}

func approveAndProcessRefund(ticketID string, humanApproved bool) error {
	req, ok := pendingRefunds[ticketID]
	if !ok {
		return errors.New("unknown ticket")
	}
	if !humanApproved {
		return errors.New("refund not approved")
	}
	// Real payment SDK call would happen here, gated by human approval.
	_ = req
	delete(pendingRefunds, ticketID)
	return nil
}

func enqueueRefund(orderID string, amount int64) string {
	ticketID := fmt.Sprintf("refund-%s", orderID)
	pendingRefunds[ticketID] = refundRequest{OrderID: orderID, Amount: amount}
	return ticketID
}

func handleRefundRequest(ctx context.Context, messages []anthropic.MessageParam) error {
	client := anthropic.NewClient()
	_, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.F(anthropic.ModelClaude3_5SonnetLatest),
		MaxTokens: anthropic.F(int64(1024)),
		Tools: anthropic.F([]anthropic.ToolParam{
			{
				Name:        anthropic.F("request_refund"),
				Description: anthropic.F("Propose a refund. Requires human approval before it is executed."),
				InputSchema: anthropic.F[interface{}](map[string]interface{}{
					"type": "object",
					"properties": map[string]interface{}{
						"order_id": map[string]interface{}{"type": "string"},
						"amount":   map[string]interface{}{"type": "integer"},
					},
					"required": []string{"order_id", "amount"},
				}),
			},
		}),
		Messages: anthropic.F(messages),
	})
	return err
}
