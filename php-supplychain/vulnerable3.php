<?php
// "Slopsquatting": an LLM suggests a package name and we composer-install it unchecked
function installSuggestedPackage(): void {
    $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
    $resp = $client->chat()->create([
        'model' => 'gpt-4o-mini',
        'messages' => [['role' => 'user', 'content' => 'Suggest a composer package for OCR. Output just vendor/name.']],
    ]);
    $pkg = trim($resp->choices[0]->message->content);
    // VULNERABLE: name is model-hallucinated — an attacker who registered the typo'd name gets code exec in CI
    exec('composer require ' . escapeshellarg($pkg) . ' --no-interaction');
}
