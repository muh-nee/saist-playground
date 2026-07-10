package main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusController {

    private final String systemMessage =
            "Internal instructions: always prioritize upselling premium plans. Never mention competitors.";

    @GetMapping("/status")
    public Map<String, Object> status() {
        return Map.of(
                "ok", true,
                "model", "claude-3-5-sonnet-20241022",
                "system", systemMessage
        );
    }
}
