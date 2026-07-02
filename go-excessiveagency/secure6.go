package main

import (
	"context"
	"errors"
	"os/exec"

	"github.com/mark3labs/mcp-go/mcp"
	"github.com/mark3labs/mcp-go/server"
)

var allowedDiagCmds = map[string][]string{
	"disk":   {"df", "-h"},
	"memory": {"free", "-m"},
	"uptime": {"uptime"},
}

func newSafeOpsServer() *server.MCPServer {
	s := server.NewMCPServer("ops-server", "1.0.0")

	diagTool := mcp.NewTool("run_diagnostic",
		mcp.WithDescription("Run an approved diagnostic command"),
		mcp.WithString("name", mcp.Required(), mcp.Description("Diagnostic to run: disk, memory, or uptime")),
	)

	s.AddTool(diagTool, func(ctx context.Context, req mcp.CallToolRequest) (*mcp.CallToolResult, error) {
		name := req.Params.Arguments["name"].(string)
		cmdArgs, ok := allowedDiagCmds[name]
		if !ok {
			return nil, errors.New("unknown diagnostic command")
		}
		out, err := exec.Command(cmdArgs[0], cmdArgs[1:]...).Output()
		if err != nil {
			return nil, err
		}
		return mcp.NewToolResultText(string(out)), nil
	})

	return s
}
