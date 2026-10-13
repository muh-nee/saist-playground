<?php
// User-controlled ONNX model URL downloaded and loaded for inference
use OnnxRuntime\InferenceSession;

function infer(array $input): array {
    $modelUrl = $_GET['model_url']; // tainted
    $local    = sys_get_temp_dir() . '/model.onnx';
    // VULNERABLE: attacker controls the model bytes; no allowlist, no digest check
    file_put_contents($local, file_get_contents($modelUrl));
    $session = new InferenceSession($local);
    return $session->run(null, $input);
}
