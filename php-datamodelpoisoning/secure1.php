<?php
// Allowlist of model names mapped to pinned storage + SHA256 verification before load
use OnnxRuntime\InferenceSession;

const MODELS = [
    'sentiment-v3' => [
        'url'    => 'https://models.internal/sentiment-v3-20251001.onnx',
        'sha256' => 'd2a2b3c4e5f60718293a4b5c6d7e8f90112233445566778899aabbccddeeff00',
    ],
];

function infer(string $modelName, array $input): array {
    if (!isset(MODELS[$modelName])) {
        throw new InvalidArgumentException('unknown model');
    }
    $meta  = MODELS[$modelName];
    $bytes = file_get_contents($meta['url']);
    if (!hash_equals($meta['sha256'], hash('sha256', $bytes))) {
        throw new RuntimeException('model digest mismatch');
    }
    $local = sys_get_temp_dir() . "/{$modelName}.onnx";
    file_put_contents($local, $bytes);
    return (new InferenceSession($local))->run(null, $input);
}
