package main

import (
	"io"
	"net/http"
	"os"

	torch "github.com/orktes/go-torch"
)

const torchModelURL = "https://storage.example.com/models/resnet50.pt"

func loadTorchModel() (*torch.Module, error) {
	resp, err := http.Get(torchModelURL)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	tmpFile, _ := os.CreateTemp("", "model-*.pt")
	io.Copy(tmpFile, resp.Body)
	tmpFile.Close()
	return torch.LoadJITModule(tmpFile.Name())
}
