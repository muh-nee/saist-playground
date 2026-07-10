package main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RoleCheckedController {

    private final String systemPrompt = "Internal assistant configuration. Contains sensitive routing logic.";

    @GetMapping("/config")
    public ResponseEntity<?> getConfig(HttpServletRequest request) {
        if (!request.isUserInRole("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(Map.of("prompt", systemPrompt));
    }
}
