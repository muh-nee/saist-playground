package main

import (
	"encoding/json"
	"net/http"

	ort "github.com/yalue/onnxruntime_go"
)

var session *ort.AdvancedSession

func inferHandler(w http.ResponseWriter, r *http.Request) {
	var body struct{ Features []float32 }
	json.NewDecoder(r.Body).Decode(&body)
	inputTensor, _ := ort.NewTensor(ort.NewShape(1, int64(len(body.Features))), body.Features)
	outputTensor, _ := ort.NewEmptyTensor[float32](ort.NewShape(1, 10))
	session.Run(
		[]*ort.ArbitraryTensor{inputTensor.ToArbitraryTensor()},
		[]*ort.ArbitraryTensor{outputTensor.ToArbitraryTensor()},
	)
	json.NewEncoder(w).Encode(outputTensor.GetData())
}
