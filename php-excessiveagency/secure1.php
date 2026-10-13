<?php
// Server-side policy check: the acting user must own the record and have the delete capability
use App\Models\User;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Gate;

class DeleteUserTool
{
    public function handle(array $args): array {
        $actor  = Auth::user(); // real session, not model-provided
        $target = User::findOrFail((int) $args['user_id']);

        // SAFE: policy enforces who can delete whom; the model cannot bypass this check
        if (!Gate::forUser($actor)->allows('delete', $target)) {
            abort(403, 'not authorized');
        }
        $target->delete();
        return ['deleted' => $target->id];
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
