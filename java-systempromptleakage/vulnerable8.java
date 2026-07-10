package main;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DiagnosticController {

    private final String systemPrompt =
            "Confidential: billing assistant with access to payment processor API keys.";

    @GetMapping("/debug")
    public ResponseEntity<Map<String, Object>> debug() {
        Map<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("model", "gpt-4o");
        debugInfo.put("prompt", systemPrompt);
        return ResponseEntity.ok(debugInfo);
    }
}
