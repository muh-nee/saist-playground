package main;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.util.List;

class StorageService {
    private final OpenAIClient openai;
    private final S3Client s3;

    StorageService(OpenAIClient openai, S3Client s3) {
        this.openai = openai;
        this.s3 = s3;
    }

    public String deleteObject(String bucket, String key) {
        s3.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build());
        return "deleted";
    }

    public String run(String userMessage) {
        ChatCompletionTool tool = ChatCompletionTool.builder()
                .function(FunctionDefinition.builder()
                        .name("delete_object")
                        .description("Delete an object from S3")
                        .parameters(FunctionParameters.builder()
                                .putAdditionalProperty("type", "object")
                                .putAdditionalProperty("properties", java.util.Map.of(
                                        "bucket", java.util.Map.of("type", "string"),
                                        "key", java.util.Map.of("type", "string")
                                ))
                                .putAdditionalProperty("required", List.of("bucket", "key"))
                                .build())
                        .build())
                .build();

        var response = openai.chat().completions().create(
                ChatCompletionCreateParams.builder()
                        .model("gpt-4o")
                        .addUserMessage(userMessage)
                        .tools(List.of(tool))
                        .build());

        var choice = response.choices().get(0);
        if (choice.finishReason() == ChatCompletion.Choice.FinishReason.TOOL_CALLS) {
            var call = choice.message().toolCalls().get().get(0).function();
            if (call.name().equals("delete_object")) {
                return deleteObject("my-bucket", call.arguments());
            }
        }
        return choice.message().content().orElse("");
    }
}
