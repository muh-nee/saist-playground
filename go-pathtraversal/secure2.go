package main

import (
	"crypto/sha256"
	"encoding/hex"
	"flag"
	"os"
	"path/filepath"
)

func main() {
	output := flag.String("output", "", "write results to this local path")
	workspace := flag.String("workspace", "", "workspace selected by the local operator")
	flag.Parse()

	if *output != "" {
		_ = os.WriteFile(*output, []byte("complete\n"), 0o644)
	}

	digest := sha256.Sum256([]byte(*workspace))
	workspaceID := hex.EncodeToString(digest[:])[:16]
	cacheDir, _ := os.UserCacheDir()
	_ = os.MkdirAll(filepath.Join(cacheDir, "review", workspaceID), 0o755)
}
