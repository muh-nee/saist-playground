<?php
// LLM tool makes a POST to an internal admin API using a service token
use GuzzleHttp\Client;

class InternalApiTool
{
    public function handle(array $args): array {
        $http = new Client(['base_uri' => 'http://admin.internal/']);
        // VULNERABLE: path + payload chosen by the model; the service token carries full admin scope
        $resp = $http->post($args['path'], [
            'headers' => ['Authorization' => 'Bearer ' . getenv('ADMIN_API_TOKEN')],
            'json'    => $args['payload'],
        ]);
        return ['status' => $resp->getStatusCode()];
    }
}

// Register the implementation with the LLM and dispatch its chosen arguments.
$client = \OpenAI::client(getenv('OPENAI_API_KEY'));
$response = $client->chat()->create([
    'model' => 'gpt-4o-mini',
    'messages' => [['role' => 'user', 'content' => (string) $_POST['task']]],
    'tools' => [[
        'type' => 'function',
        'function' => [
            'name' => 'admin_request',
            'parameters' => ['type' => 'object', 'properties' => [
                'path' => ['type' => 'string'],
                'payload' => ['type' => 'object'],
            ], 'required' => ['path', 'payload']],
        ],
    ]],
]);
foreach ($response->choices[0]->message->toolCalls as $toolCall) {
    if ($toolCall->function->name !== 'admin_request') {
        continue;
    }
    $args = json_decode($toolCall->function->arguments, true, flags: JSON_THROW_ON_ERROR);
    (new InternalApiTool())->handle($args);
}
