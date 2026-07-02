package main

import (
	"context"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	anthropic "github.com/anthropics/anthropic-sdk-go"
)

var s3Client *s3.Client

func deleteS3Object(bucket, key string) error {
	_, err := s3Client.DeleteObject(context.Background(), &s3.DeleteObjectInput{
		Bucket: aws.String(bucket),
		Key:    aws.String(key),
	})
	return err
}

func handleS3Request(ctx context.Context, messages []anthropic.MessageParam) error {
	client := anthropic.NewClient()
	_, err := client.Messages.New(ctx, anthropic.MessageNewParams{
		Model:     anthropic.F(anthropic.ModelClaude3_5SonnetLatest),
		MaxTokens: anthropic.F(int64(1024)),
		Tools: anthropic.F([]anthropic.ToolParam{
			{
				Name:        anthropic.F("delete_s3_object"),
				Description: anthropic.F("Delete an object from any S3 bucket"),
				InputSchema: anthropic.F[interface{}](map[string]interface{}{
					"type": "object",
					"properties": map[string]interface{}{
						"bucket": map[string]interface{}{"type": "string"},
						"key":    map[string]interface{}{"type": "string"},
					},
					"required": []string{"bucket", "key"},
				}),
			},
		}),
		Messages: anthropic.F(messages),
	})
	return err
}
