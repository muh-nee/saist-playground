package main

import (
	"crypto/sha256"
	"fmt"
	"io"
	"net/http"
	"os"
	"plugin"
)

const pluginURL = "https://plugins.example.com/ai/classifier.so"
const expectedPluginHash = "deadbeef1234567890abcdef1234567890abcdef1234567890abcdef12345678"

func loadVerifiedPlugin() (*plugin.Plugin, error) {
	resp, err := http.Get(pluginURL)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	pluginBytes, _ := io.ReadAll(resp.Body)
	if fmt.Sprintf("%x", sha256.Sum256(pluginBytes)) != expectedPluginHash {
		return nil, fmt.Errorf("plugin integrity check failed")
	}
	tmpFile, _ := os.CreateTemp("", "plugin-*.so")
	tmpFile.Write(pluginBytes)
	tmpFile.Close()
	return plugin.Open(tmpFile.Name())
}
