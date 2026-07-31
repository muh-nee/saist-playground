package main

import (
	"log"

	torch "github.com/orktes/go-torch"
)

const torchModelPath = "./models/resnet50.pt"

func loadModule() *torch.Module {
	module, err := torch.LoadJITModule(torchModelPath)
	if err != nil {
		log.Fatalf("failed to load module: %v", err)
	}
	return module
}
