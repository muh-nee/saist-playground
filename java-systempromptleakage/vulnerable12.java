import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class vulnerable12 {

    private final ChatLanguageModel model;

    public vulnerable12(ChatLanguageModel model) {
        this.model = model;
    }

    private static final List<ToolSpecification> tools = List.of(
        ToolSpecification.builder()
            .name("escalate_ticket")
            .description("Escalates to internal tier-2. Route: support-internal@corp.com")
            .build()
    );

    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestParam String userMessage) {
        ChatRequest request = ChatRequest.builder()
            .messages(List.of(dev.langchain4j.data.message.UserMessage.from(userMessage)))
            .toolSpecifications(tools)
            .build();
        model.chat(request);
        return ResponseEntity.ok(Map.of("reply", "ok"));
    }

    @GetMapping("/debug/tools")
    public ResponseEntity<Map<String, Object>> getTools() {
        return ResponseEntity.ok(Map.of("tools", tools));
    }
}
