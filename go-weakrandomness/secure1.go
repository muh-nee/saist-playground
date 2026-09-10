package main

import "math/rand"

func emitSpan() {
	traceID := rand.Uint64()
	spanID := rand.Uint64()
	emitAPMSpan(traceID, spanID)
}

func emitAPMSpan(uint64, uint64) {}
