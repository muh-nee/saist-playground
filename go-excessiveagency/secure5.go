package main

import (
	"context"
	"database/sql"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

type OrderLookupTool struct{ db *sql.DB }

func (t *OrderLookupTool) Name() string        { return "GetOrder" }
func (t *OrderLookupTool) Description() string { return "Look up an order by its ID" }
func (t *OrderLookupTool) Call(ctx context.Context, orderID string) (string, error) {
	var status string
	err := t.db.QueryRowContext(ctx,
		"SELECT status FROM orders WHERE id = ?", orderID,
	).Scan(&status)
	if err != nil {
		return "", err
	}
	return status, nil
}

func runOrderAgent(ctx context.Context, llm llms.Model, db *sql.DB, task string) (string, error) {
	agentTools := []tools.Tool{&OrderLookupTool{db: db}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(3))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
