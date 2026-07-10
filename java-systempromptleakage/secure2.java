package main;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {

    private final String systemPrompt = "Internal assistant with access to support tooling and escalation paths.";

    @GetMapping("/admin/prompt")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> viewPrompt() {
        return ResponseEntity.ok(Map.of("prompt", systemPrompt));
    }
}
