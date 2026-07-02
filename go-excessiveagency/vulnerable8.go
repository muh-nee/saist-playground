package main

import (
	"context"
	"os"

	"github.com/mark3labs/mcp-go/mcp"
	"github.com/mark3labs/mcp-go/server"
)

func newFileServer() *server.MCPServer {
	s := server.NewMCPServer("file-server", "1.0.0")

	writeTool := mcp.NewTool("write_file",
		mcp.WithDescription("Write content to a file"),
		mcp.WithString("path", mcp.Required(), mcp.Description("File path to write")),
		mcp.WithString("content", mcp.Required(), mcp.Description("Content to write")),
	)

	s.AddTool(writeTool, func(ctx context.Context, req mcp.CallToolRequest) (*mcp.CallToolResult, error) {
		path := req.Params.Arguments["path"].(string)
		content := req.Params.Arguments["content"].(string)
		err := os.WriteFile(path, []byte(content), 0644)
		if err != nil {
			return nil, err
		}
		return mcp.NewToolResultText("written"), nil
	})

	return s
}
