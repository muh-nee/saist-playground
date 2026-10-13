<?php
// Unpinned HuggingFace artifact fetched with Guzzle; no commit SHA, no digest check
use GuzzleHttp\Client;

function downloadEmbedder(string $destDir): string {
    $http = new Client(['timeout' => 60]);
    // VULNERABLE: ref=main is mutable; owner can swap the weights at any time
    $url = 'https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2/resolve/main/pytorch_model.bin';
    $body = (string) $http->get($url)->getBody();
    $dest = $destDir . '/pytorch_model.bin';
    file_put_contents($dest, $body); // no SHA256 verification before use
    return $dest;
}
