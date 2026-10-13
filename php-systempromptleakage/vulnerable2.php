<?php
// Full system prompt + per-request augmentation logged at debug level
use Monolog\Logger;
use Monolog\Handler\StreamHandler;

const SYSTEM_PROMPT = "You are the triage assistant. Secret heuristic: escalate anything mentioning 'enterprise-tier'.";

function chat(string $userMsg): string {
    $logger = new Logger('chat');
    $logger->pushHandler(new StreamHandler('/var/log/chat.log', Logger::DEBUG));

    $messages = [
        ['role' => 'system', 'content' => SYSTEM_PROMPT],
        ['role' => 'user',   'content' => $userMsg],
    ];
    // VULNERABLE: full prompt (including SYSTEM_PROMPT) persisted to a widely-read log
    $logger->debug('about to call LLM', ['messages' => $messages]);

    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    return $client->chat()->create(['model' => 'gpt-4o-mini', 'messages' => $messages])
        ->choices[0]->message->content;
}
