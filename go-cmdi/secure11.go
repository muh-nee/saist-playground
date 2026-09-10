package main

import (
	"context"
	"os/exec"
)

type containerSpec struct {
	Command []string
	Args    []string
}

func runAtlas(ctx context.Context, cluster string) error {
	cmd := exec.CommandContext(ctx, "atlas", "--request-clusters", cluster)
	return cmd.Run()
}

func memorystoreInitContainer() containerSpec {
	return containerSpec{
		Command: []string{"/bin/bash", "-c"},
		Args:    []string{"fetch-memorystore-certs"},
	}
}
