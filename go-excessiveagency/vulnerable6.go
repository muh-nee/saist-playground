package main

import (
	"context"
	"database/sql"

	"github.com/tmc/langchaingo/agents"
	"github.com/tmc/langchaingo/llms"
	"github.com/tmc/langchaingo/tools"
)

type DBWriteTool struct{ db *sql.DB }

func (t *DBWriteTool) Name() string { return "ExecuteSQL" }
func (t *DBWriteTool) Description() string {
	return "Execute an arbitrary SQL statement (INSERT/UPDATE/DELETE) against the application database"
}
func (t *DBWriteTool) Call(ctx context.Context, sqlStmt string) (string, error) {
	if _, err := t.db.ExecContext(ctx, sqlStmt); err != nil {
		return "", err
	}
	return "ok", nil
}

func runDBWriteAgent(ctx context.Context, llm llms.Model, db *sql.DB, task string) (string, error) {
	agentTools := []tools.Tool{&DBWriteTool{db: db}}
	agent := agents.NewOneShotAgent(llm, agentTools, agents.WithMaxIterations(5))
	executor := agents.NewExecutor(agent)
	return executor.Run(ctx, task)
}
