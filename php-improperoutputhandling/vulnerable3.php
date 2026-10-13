<?php
// LLM picks the template filename; result is include()'d — remote/local file inclusion
use Prism\Prism\Prism;
use Prism\Prism\Enums\Provider;

function renderSuggestedTemplate(string $topic): void {
    $picked = Prism::text()
        ->using(Provider::OpenAI, 'gpt-4o-mini')
        ->withPrompt("Which template file best fits topic '{$topic}'? Return a path.")
        ->asText()
        ->text;
    // VULNERABLE: LLM output used as include path — supports ../, PHP wrappers, URL schemes
    include $picked;
}
