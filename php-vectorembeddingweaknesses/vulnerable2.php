<?php
// User-submitted HTML upserted into Pinecone with no sanitation or source allowlist
use GuzzleHttp\Client;

function upsertHtml(string $id): void {
    $html = $_POST['html']; // raw user HTML — can contain injected instructions, script, etc.
    $http = new Client([
        'base_uri' => 'https://my-index.svc.pinecone.io/',
        'headers'  => ['Api-Key' => getenv('PINECONE_API_KEY')],
    ]);
    // VULNERABLE: no stripping of hidden tokens; HTML persists as-is in retrieval context
    $http->post('/vectors/upsert', [
        'json' => [
            'vectors' => [[
                'id'       => $id,
                'values'   => embed($html),
                'metadata' => ['html' => $html],
            ]],
        ],
    ]);
}

function embed(string $t): array { return array_fill(0, 1536, 0.0); }
