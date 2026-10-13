<?php
// Cap output tokens AND bound the HTTP timeout at the transport layer
use GuzzleHttp\Client as GuzzleClient;

function ask(string $prompt): string {
    // SAFE: HTTP client enforces connect + overall timeout
    $http = new GuzzleClient([
        'connect_timeout' => 2,
        'timeout'         => 15,
    ]);
    $client = \OpenAI::factory()
        ->withApiKey(getenv('OPENAI_API_KEY'))
        ->withHttpClient($http)
        ->make();

    $resp = $client->chat()->create([
        'model'       => 'gpt-4o-mini',
        'messages'    => [['role' => 'user', 'content' => $prompt]],
        'max_tokens'  => 512, // SAFE: hard output cap
        'temperature' => 0.2,
    ]);
    return $resp->choices[0]->message->content;
}
