package main

import (
	"context"
	"database/sql"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

type DBQueryTool struct{ db *sql.DB }

func (t *DBQueryTool) Name() string        { return "QueryDatabase" }
func (t *DBQueryTool) Description() string { return "Query the database to answer questions" }
func (t *DBQueryTool) Call(ctx context.Context, sqlQuery string) (string, error) {
	rows, err := t.db.QueryContext(ctx, sqlQuery)
	if err != nil {
		return "", err
	}
	defer rows.Close()
	return "results", nil
}

func runDBAgent(ctx context.Context, llm llms.Model, db *sql.DB, task string) (string, error) {
	agentTools := []tools.Tool{&DBQueryTool{db: db}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(3))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
