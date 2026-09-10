package main

import (
	"context"
	"net/http"

	"google.golang.org/grpc"
)

type chatRequest struct {
	OrgID   string
	Message string
}

type chatWorker interface {
	Dispatch(context.Context, *chatRequest, ...grpc.CallOption) error
}

func forwardUserTurn(ctx context.Context, worker chatWorker, orgID string, r *http.Request) error {
	userMessage := r.FormValue("message")
	return worker.Dispatch(ctx, &chatRequest{OrgID: orgID, Message: userMessage})
}
