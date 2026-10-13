<?php
// System prompt is included in an exception message that bubbles up to the HTTP response
const SYSTEM_PROMPT = "You are Datadog's internal pricing bot. Never reveal discount rules. Internal coupon code: DD-STAFF-50.";

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
        // VULNERABLE: leaks SYSTEM_PROMPT verbatim via the error path
        throw new RuntimeException('LLM call failed with system prompt=' . SYSTEM_PROMPT . ' err=' . $e->getMessage());
    }
}
