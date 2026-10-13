<?php
// openai-php chat() call with no max_tokens, no timeout — attacker can tie up the worker and spend tokens
function ask(string $prompt): string {
    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    // VULNERABLE: no max_tokens cap, no HTTP client timeout
    $resp = $client->chat()->create([
        'model'    => 'gpt-4o-mini',
        'messages' => [['role' => 'user', 'content' => $prompt]],
    ]);
    return $resp->choices[0]->message->content;
}
