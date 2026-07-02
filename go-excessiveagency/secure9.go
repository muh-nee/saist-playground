package main

import (
	"context"
	"errors"
	"os"
	"path/filepath"
	"strings"

	"github.com/mark3labs/mcp-go/mcp"
	"github.com/mark3labs/mcp-go/server"
)

const mcpAllowedRoot = "/var/app/uploads"

func newSafeFileServer() *server.MCPServer {
	s := server.NewMCPServer("file-server", "1.0.0")

	readTool := mcp.NewTool("read_file",
		mcp.WithDescription("Read a file from the uploads directory"),
		mcp.WithString("path", mcp.Required(), mcp.Description("Relative path within uploads directory")),
	)

	s.AddTool(readTool, func(ctx context.Context, req mcp.CallToolRequest) (*mcp.CallToolResult, error) {
		path := req.Params.Arguments["path"].(string)
		absPath, err := filepath.Abs(filepath.Join(mcpAllowedRoot, path))
		if err != nil || !strings.HasPrefix(absPath, mcpAllowedRoot+string(os.PathSeparator)) {
			return nil, errors.New("path outside allowed directory")
		}
		data, err := os.ReadFile(absPath)
		if err != nil {
			return nil, err
		}
		return mcp.NewToolResultText(string(data)), nil
	})

	return s
}
