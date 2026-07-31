package main

import (
	"io"
	"net/http"
	"os"
	"plugin"
)

const pluginURL = "https://plugins.example.com/ai/classifier.so"

func loadPlugin() (*plugin.Plugin, error) {
	resp, err := http.Get(pluginURL)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	tmpFile, _ := os.CreateTemp("", "plugin-*.so")
	io.Copy(tmpFile, resp.Body)
	tmpFile.Close()
	return plugin.Open(tmpFile.Name())
}
