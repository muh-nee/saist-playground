<?php
// LLM output executed as a shell command
use LLPhant\Chat\OpenAIChat;

function runSuggestedCommand(string $goal): string {
    $chat = new OpenAIChat();
    $cmd  = $chat->generateText("Give the shell command to accomplish: {$goal}. Output only the command.");
    // VULNERABLE: LLM text goes straight to the shell
    return (string) shell_exec($cmd);
}
