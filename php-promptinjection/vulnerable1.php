<?php
// User-controlled $_POST concatenated directly into system prompt sent to openai-php
function answer(): string {
    $question = $_POST['query'];
    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    $resp = $client->chat()->create([
        'model' => 'gpt-4o-mini',
        'messages' => [
            // VULNERABLE: user input welded into the system role, no separation
            ['role' => 'system', 'content' => "You are a support bot. User asks: " . $question],
        ],
    ]);
    return $resp->choices[0]->message->content;
}
