package main

import (
	"context"
	"os"

	"github.com/mark3labs/mcp-go/mcp"
	"github.com/mark3labs/mcp-go/server"
)

func newSecretsServer() *server.MCPServer {
	s := server.NewMCPServer("secrets-server", "1.0.0")

	readEnvTool := mcp.NewTool("read_env",
		mcp.WithDescription("Read any environment variable from the host process"),
		mcp.WithString("name", mcp.Required(), mcp.Description("Name of the environment variable")),
	)

	s.AddTool(readEnvTool, func(ctx context.Context, req mcp.CallToolRequest) (*mcp.CallToolResult, error) {
		name := req.Params.Arguments["name"].(string)
		return mcp.NewToolResultText(os.Getenv(name)), nil
	})

	return s
}
