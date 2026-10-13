<?php
// Ask the LLM for structured parameters; apply them via a prepared statement
function search(PDO $pdo, string $question): array {
    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    $resp = $client->chat()->create([
        'model' => 'gpt-4o-mini',
        'response_format' => ['type' => 'json_object'],
        'messages' => [[
            'role' => 'user',
            'content' => "Return JSON {\"status\":\"...\",\"min_total\":<int>} for: {$question}",
        ]],
    ]);
    $parsed = json_decode($resp->choices[0]->message->content, true, 8, JSON_THROW_ON_ERROR);
    $allowedStatus = ['open', 'shipped', 'cancelled'];
    if (!in_array($parsed['status'] ?? null, $allowedStatus, true) || !is_int($parsed['min_total'] ?? null)) {
        throw new InvalidArgumentException('model returned bad params');
    }
    // SAFE: fixed SQL, prepared parameters, allowlisted status
    $stmt = $pdo->prepare('SELECT * FROM orders WHERE status = ? AND total >= ?');
    $stmt->execute([$parsed['status'], $parsed['min_total']]);
    return $stmt->fetchAll();
}
