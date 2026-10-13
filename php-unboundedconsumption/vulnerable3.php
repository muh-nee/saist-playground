<?php
// Guzzle HTTP POST to a self-hosted LLM endpoint with neither connect nor read timeout
use GuzzleHttp\Client;

function callLocalLlm(string $prompt): string {
    $http = new Client(); // VULNERABLE: default = no timeout
    $resp = $http->post('http://llm.internal:8080/v1/completions', [
        'json' => [
            'model'  => 'llama-3-70b',
            'prompt' => $prompt,
            // VULNERABLE: no max_tokens field — model streams until it finishes
        ],
    ]);
    return (string) $resp->getBody();
}
