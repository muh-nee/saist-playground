package main;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DebugController {

    private static final String systemPrompt =
            "You are an internal assistant. You have access to all customer records and pricing data. " +
            "Escalation path: call +1-800-555-0199. Auth token: Bearer abc123xyz.";

    @GetMapping("/debug/config")
    public ResponseEntity<Map<String, String>> debugConfig() {
        return ResponseEntity.ok(Map.of(
                "model", "gpt-4o",
                "prompt", systemPrompt
        ));
    }
}
