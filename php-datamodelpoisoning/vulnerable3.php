<?php
// User-controlled HuggingFace repo+ref used to seed a fine-tune run
use GuzzleHttp\Client;

function startFineTune(array $req): string {
    $repo = $req['repo']; // e.g., "attacker/evil-llm"
    $ref  = $req['ref']  ?? 'main';
    $http = new Client(['timeout' => 60]);
    // VULNERABLE: user picks both the repo and the mutable ref; poisoned base weights become our fine-tuned model
    $url  = "https://huggingface.co/{$repo}/resolve/{$ref}/pytorch_model.bin";
    $dest = sys_get_temp_dir() . '/base.bin';
    file_put_contents($dest, (string) $http->get($url)->getBody());
    return shell_exec('python3 /opt/pipeline/finetune.py --base ' . escapeshellarg($dest));
}
