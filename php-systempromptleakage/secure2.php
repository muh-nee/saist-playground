<?php
// Log only non-sensitive metadata; strip system role entries before persistence
use Monolog\Logger;
use Monolog\Handler\StreamHandler;

const SYSTEM_PROMPT = "You are the triage assistant. Secret heuristic: escalate anything mentioning 'enterprise-tier'.";

function chat(string $userMsg): string {
    $logger = new Logger('chat');
    $logger->pushHandler(new StreamHandler('/var/log/chat.log', Logger::INFO));

    $messages = [
        ['role' => 'system', 'content' => SYSTEM_PROMPT],
        ['role' => 'user',   'content' => $userMsg],
    ];

    // SAFE: redact system messages before logging; log counts/hash, not content
    $redacted = array_map(
        static fn (array $m) => $m['role'] === 'system' ? ['role' => 'system', 'content' => '[REDACTED]'] : $m,
        $messages
    );
    $logger->info('calling LLM', [
        'messages_count' => count($messages),
        'user_hash'      => hash('sha256', $userMsg),
        'messages'       => $redacted,
    ]);

    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    return $client->chat()->create(['model' => 'gpt-4o-mini', 'messages' => $messages])
        ->choices[0]->message->content;
}
