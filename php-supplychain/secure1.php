<?php
// HuggingFace artifact pinned to immutable commit SHA + SHA256 verified before use
use GuzzleHttp\Client;

const MODEL_COMMIT = 'e4ce9877abf3edfe10b0d82785e83bdcb973e22e';
const MODEL_SHA256 = 'c3d2a9a4e2a7a1f9bf0a9cf6c2c6a4b6e8d1e7b2c9a0f1d2e3f4a5b6c7d8e9f0';

function downloadEmbedder(string $destDir): string {
    $http = new Client(['timeout' => 60]);
    // SAFE: ref pinned to a specific commit so the owner can't swap bytes
    $url  = 'https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2/resolve/' . MODEL_COMMIT . '/pytorch_model.bin';
    $body = (string) $http->get($url)->getBody();
    if (!hash_equals(MODEL_SHA256, hash('sha256', $body))) {
        throw new RuntimeException('model digest mismatch; refusing to install');
    }
    $dest = $destDir . '/pytorch_model.bin';
    file_put_contents($dest, $body);
    return $dest;
}
