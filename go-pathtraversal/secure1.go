package main

import (
	"fmt"
	"os"
	"path/filepath"
)

func initCacheDir() (string, error) {
	baseDir := os.Getenv("APP_CACHE_BASE_DIR")
	if baseDir == "" {
		var err error
		baseDir, err = os.MkdirTemp("", "app_cache_*")
		if err != nil {
			return "", fmt.Errorf("failed to create temp dir: %w", err)
		}
	}

	cacheRoot := filepath.Join(baseDir, ".app-cache")
	if err := os.MkdirAll(cacheRoot, 0o755); err != nil {
		return "", fmt.Errorf("failed to create cache dir: %w", err)
	}

	metaPath := filepath.Join(baseDir, "metadata.json")
	if _, err := os.Stat(metaPath); os.IsNotExist(err) {
		if err := os.WriteFile(metaPath, []byte("{}"), 0o644); err != nil {
			return "", fmt.Errorf("failed to write metadata: %w", err)
		}
	}

	return cacheRoot, nil
}

func main() {
	dir, err := initCacheDir()
	if err != nil {
		fmt.Printf("Error: %v\n", err)
		os.Exit(1)
	}
	fmt.Printf("Cache dir: %s\n", dir)
}

