<?php
// Prism streaming consumer with no byte/time ceiling — model can stream forever
use Prism\Prism\Prism;
use Prism\Prism\Enums\Provider;

function streamAnswer(string $prompt): string {
    $buf = '';
    $stream = Prism::text()
        ->using(Provider::OpenAI, 'gpt-4o-mini')
        ->withPrompt($prompt)
        ->asStream();
    // VULNERABLE: no max_tokens, no overall deadline, no byte cap
    foreach ($stream as $chunk) {
        $buf .= $chunk->text;
    }
    return $buf;
}

