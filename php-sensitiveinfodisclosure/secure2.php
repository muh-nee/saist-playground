<?php
// Monolog processor strips secrets and PII before anything reaches the handler
use Monolog\Logger;
use Monolog\Handler\StreamHandler;

function logLlmFailure(\Throwable $e): void {
    $logger = new Logger('llm');
    $logger->pushHandler(new StreamHandler('/var/log/app.log'));
    $logger->pushProcessor(static function (array $record): array {
        // SAFE: redact known-sensitive keys before serialization
        $drop = ['authorization', 'cookie', 'openai_key', 'ssn', 'password'];
        $walk = static function (array &$a) use (&$walk, $drop): void {
            foreach ($a as $k => &$v) {
                if (in_array(strtolower((string) $k), $drop, true)) { $v = '[REDACTED]'; continue; }
                if (is_array($v)) { $walk($v); }
            }
        };
        $walk($record['context']);
        return $record;
    });
    $logger->error('LLM call failed', [
        'route'     => $_SERVER['REQUEST_URI'] ?? '',
        'status'    => 500,
        'exception' => $e->getMessage(), // message only, no trace / env
    ]);
}
