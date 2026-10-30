<?php
// LLM-exposed tool deletes users without any server-side authorization check
use App\Models\User;

class DeleteUserTool
{
    public static function schema(): array {
        return ['name' => 'delete_user', 'parameters' => ['user_id' => 'int']];
    }

    public function handle(array $args): array {
        $id = (int) $args['user_id'];
        // VULNERABLE: model-decided ID is used directly; no caller/role check
        User::destroy($id);
        return ['deleted' => $id];
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
            'name' => 'delete_user',
            'parameters' => ['type' => 'object', 'properties' => [
                'user_id' => ['type' => 'integer'],
            ], 'required' => ['user_id']],
        ],
    ]],
]);
foreach ($response->choices[0]->message->toolCalls as $toolCall) {
    if ($toolCall->function->name !== 'delete_user') {
        continue;
    }
    $args = json_decode($toolCall->function->arguments, true, flags: JSON_THROW_ON_ERROR);
    (new DeleteUserTool())->handle($args);
}

