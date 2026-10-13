<?php
// Mail tool queues a draft requiring human approval; recipients must be in the actor's existing contacts
use App\Models\DraftEmail;
use Illuminate\Support\Facades\Auth;

class SendEmailTool
{
    public function handle(array $args): array {
        $actor = Auth::user();
        $to    = (string) $args['to'];

        // SAFE: can only draft to addresses the real user already corresponds with
        if (!$actor->contacts()->where('email', $to)->exists()) {
            abort(403, 'recipient not in actor contacts');
        }
        if (mb_strlen((string) $args['body']) > 10_000) {
            abort(422, 'body too long');
        }

        // SAFE: nothing is actually sent — queued for human review in the UI
        $draft = DraftEmail::create([
            'author_id' => $actor->id,
            'to'        => $to,
            'subject'   => (string) $args['subject'],
            'body'      => (string) $args['body'],
            'status'    => 'pending_review',
        ]);
        return ['draft_id' => $draft->id];
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
            'name' => 'send_email',
            'parameters' => ['type' => 'object', 'properties' => [
                'to' => ['type' => 'string'],
                'subject' => ['type' => 'string'],
                'body' => ['type' => 'string'],
            ], 'required' => ['to', 'subject', 'body']],
        ],
    ]],
]);
foreach ($response->choices[0]->message->toolCalls as $toolCall) {
    if ($toolCall->function->name !== 'send_email') {
        continue;
    }
    $args = json_decode($toolCall->function->arguments, true, flags: JSON_THROW_ON_ERROR);
    (new SendEmailTool())->handle($args);
}
