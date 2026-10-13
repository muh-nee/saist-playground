<?php
// Redact PII and send only the fields the model actually needs
function recommendProducts(array $user): string {
    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    // SAFE: only non-identifying attributes make the trip
    $safe = [
        'age_band'       => ageBand((int) $user['age']),
        'country'        => (string) $user['country'],
        'segment'        => (string) $user['segment'],
        'recent_cats'    => array_slice((array) $user['categories'], 0, 5),
    ];
    $resp = $client->chat()->create([
        'model' => 'gpt-4o-mini',
        'messages' => [
            ['role' => 'system', 'content' => 'Recommend products from non-PII attributes only.'],
            ['role' => 'user',   'content' => json_encode($safe, JSON_THROW_ON_ERROR)],
        ],
    ]);
    return $resp->choices[0]->message->content;
}

function ageBand(int $age): string {
    return $age < 25 ? '18-24' : ($age < 40 ? '25-39' : ($age < 60 ? '40-59' : '60+'));
}
