package main

import (
	"fmt"
	"math/rand"
	"time"
)

func init() {
	rand.Seed(time.Now().UnixNano())
}

func generateSessionToken() string {
	return fmt.Sprintf("session-%016x", rand.Uint64())
}
