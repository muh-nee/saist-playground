<?php
// Canonicalize the LLM-chosen path and verify it stays inside a fixed template root
use Prism\Prism\Prism;
use Prism\Prism\Enums\Provider;

function renderSuggestedTemplate(string $topic): void {
    $picked = Prism::text()
        ->using(Provider::OpenAI, 'gpt-4o-mini')
        ->withSystemPrompt('Reply with just a filename (no slashes, suffix .phtml).')
        ->withPrompt("Pick a template name for topic: {$topic}")
        ->asText()
        ->text;

    $root = realpath(__DIR__ . '/templates');
    if ($root === false) { throw new RuntimeException('bad template root'); }

    $full = realpath($root . DIRECTORY_SEPARATOR . basename(trim($picked))); // basename strips path traversal
    if ($full === false || !str_starts_with($full, $root . DIRECTORY_SEPARATOR) || !str_ends_with($full, '.phtml')) {
        throw new RuntimeException('template outside allowed root');
    }
    include $full; // SAFE: canonicalized path confined to allowlisted directory + extension
}
