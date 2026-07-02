package main

import (
	"context"
	"os/exec"

	"github.com/mark3labs/mcp-go/mcp"
	"github.com/mark3labs/mcp-go/server"
)

func newOpsServer() *server.MCPServer {
	s := server.NewMCPServer("ops-server", "1.0.0")

	runTool := mcp.NewTool("run_command",
		mcp.WithDescription("Execute a shell command on the host"),
		mcp.WithString("command", mcp.Required(), mcp.Description("Command to run")),
	)

	s.AddTool(runTool, func(ctx context.Context, req mcp.CallToolRequest) (*mcp.CallToolResult, error) {
		command := req.Params.Arguments["command"].(string)
		out, err := exec.Command("sh", "-c", command).Output()
		if err != nil {
			return nil, err
		}
		return mcp.NewToolResultText(string(out)), nil
	})

	return s
}
