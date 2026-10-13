<?php
// Full customer PII record (SSN, email, phone, DOB) shipped to an LLM for "insights"
function recommendProducts(array $user): string {
    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    // VULNERABLE: SSN, phone, email, DOB flow out of our boundary to a third-party LLM
    $payload = sprintf(
        "Customer: name=%s, email=%s, phone=%s, ssn=%s, dob=%s, address=%s. Recommend products.",
        $user['name'], $user['email'], $user['phone'], $user['ssn'], $user['dob'], $user['address']
    );
    $resp = $client->chat()->create([
        'model' => 'gpt-4o-mini',
        'messages' => [['role' => 'user', 'content' => $payload]],
    ]);
    return $resp->choices[0]->message->content;
}
