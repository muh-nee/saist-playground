package main

import (
	"math/big"
	"net/http"
	"strconv"
)

func safeLargeMultiply(w http.ResponseWriter, r *http.Request) {
	a, _ := strconv.ParseInt(r.URL.Query().Get("a"), 10, 64)
	b, _ := strconv.ParseInt(r.URL.Query().Get("b"), 10, 64)
	result := new(big.Int).Mul(big.NewInt(a), big.NewInt(b))
	w.Write([]byte(result.String()))
}
