<?php
// Bounded streaming consumer: wall-clock deadline, byte cap, max_tokens
use Prism\Prism\Prism;
use Prism\Prism\Enums\Provider;

const MAX_BYTES    = 64_000;
const WALL_SECONDS = 20;

function streamAnswer(string $prompt): string {
    $stream = Prism::text()
        ->using(Provider::OpenAI, 'gpt-4o-mini')
        ->withMaxTokens(512)              // SAFE: output cap
        ->withClientOptions(['timeout' => WALL_SECONDS]) // SAFE: HTTP deadline
        ->withPrompt($prompt)
        ->asStream();

    $deadline = microtime(true) + WALL_SECONDS;
    $buf      = '';
    foreach ($stream as $chunk) {
        $buf .= $chunk->text;
        if (strlen($buf) >= MAX_BYTES || microtime(true) >= $deadline) {
            break; // SAFE: bounded by bytes and wall clock
        }
    }
    return $buf;
}
