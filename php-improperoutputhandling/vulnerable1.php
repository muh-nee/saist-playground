<?php
// LLM is asked to produce a WHERE clause; the raw text is spliced into a PDO query
function search(PDO $pdo, string $question): array {
    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    $resp = $client->chat()->create([
        'model' => 'gpt-4o-mini',
        'messages' => [[
            'role' => 'user',
            'content' => "Translate to a SQL WHERE clause over 'orders': {$question}",
        ]],
    ]);
    $where = $resp->choices[0]->message->content; // untrusted LLM output
    // VULNERABLE: LLM output executed as raw SQL — classic SQLi via prompt injection
    return $pdo->query("SELECT * FROM orders WHERE " . $where)->fetchAll();
}
