package main

import (
	"fmt"
	"net/http"

	"github.com/sirupsen/logrus"
)

func processUser(w http.ResponseWriter, r *http.Request) {
	username := r.URL.Query().Get("username")
	logger := logrus.New()
	logger.SetFormatter(&logrus.TextFormatter{DisableColors: true, DisableQuote: true})
	logger.Error(fmt.Sprintf("failed for user: %s", username))
	w.WriteHeader(http.StatusBadRequest)
}
