package main;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RedactedPromptController {

    private final String systemPrompt = "Proprietary assistant instructions.";

    @GetMapping("/debug/prompt-info")
    public ResponseEntity<Map<String, Object>> promptInfo() {
        return ResponseEntity.ok(Map.of(
                "prompt_length", systemPrompt.length(),
                "prompt_preview", "[REDACTED]"
        ));
    }
}
