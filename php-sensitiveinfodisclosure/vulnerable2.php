<?php
// Entire $_SERVER (headers, cookies, auth) and raw API key written to a shared log
use Monolog\Logger;
use Monolog\Handler\StreamHandler;

function logLlmFailure(\Throwable $e): void {
    $logger = new Logger('llm');
    $logger->pushHandler(new StreamHandler('/var/log/app.log'));
    // VULNERABLE: $_SERVER contains Authorization headers, cookies; OPENAI_API_KEY dumped verbatim
    $logger->error('LLM call failed', [
        'env' => $_SERVER,
        'openai_key' => getenv('OPENAI_API_KEY'),
        'request_body' => file_get_contents('php://input'),
        'exception' => $e->getMessage(),
    ]);
}
