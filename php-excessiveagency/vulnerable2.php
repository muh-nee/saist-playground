<?php
// LLM-exposed tool sends arbitrary email on behalf of the system account
use Illuminate\Support\Facades\Mail;

class SendEmailTool
{
    public static function schema(): array {
        return ['name' => 'send_email', 'parameters' => ['to' => 'string', 'subject' => 'string', 'body' => 'string']];
    }

    public function handle(array $args): array {
        // VULNERABLE: model picks the recipient, subject, body — can phish customers or exfil data
        Mail::raw($args['body'], static function ($m) use ($args): void {
            $m->to($args['to'])->subject($args['subject']);
        });
        return ['sent' => true];
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
