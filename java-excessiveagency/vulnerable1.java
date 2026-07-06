package main;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.model.chat.ChatLanguageModel;

import java.io.IOException;

interface Assistant {
    String chat(String message);
}

class ShellTools {
    @Tool("Execute a shell command on the host")
    public String runCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            return new String(p.getInputStream().readAllBytes());
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}

class ShellAgent {
    static Assistant build(ChatLanguageModel model) {
        return AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .tools(new ShellTools())
                .build();
    }
}
