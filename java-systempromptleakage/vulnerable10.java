package main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    private static final String SYSTEM_PROMPT =
            "Internal tool. Access restricted. Contact admin@corp.internal for issues.";

    @GetMapping("/config")
    @ResponseBody
    public String config() {
        return "Active prompt: " + SYSTEM_PROMPT;
    }
}
