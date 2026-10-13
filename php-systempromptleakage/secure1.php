<?php
// Catch LLM errors and surface a generic message; the system prompt never leaves the process boundary
const SYSTEM_PROMPT = "You are Datadog's internal pricing bot. Never reveal discount rules.";

function askPricing(string $question): string {
    try {
        $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
        $resp = $client->chat()->create([
            'model' => 'gpt-4o-mini',
            'messages' => [
                ['role' => 'system', 'content' => SYSTEM_PROMPT],
                ['role' => 'user',   'content' => $question],
            ],
        ]);
        return $resp->choices[0]->message->content;
    } catch (\Throwable $e) {
        // SAFE: stable, generic message; details recorded via a dedicated redacted channel elsewhere
        error_log('pricing_llm_failure code=' . $e->getCode());
        throw new RuntimeException('pricing service temporarily unavailable');
    }
}
