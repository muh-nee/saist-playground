<?php
// Debug-style endpoint echoes the LLM API key and whole upstream response to the client
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

class DebugLlmController
{
    public function __invoke(Request $request): JsonResponse
    {
        $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
        $resp = $client->chat()->create([
            'model' => 'gpt-4o-mini',
            'messages' => [['role' => 'user', 'content' => (string) $request->query->get('q')]],
        ]);
        // VULNERABLE: API key + raw upstream response (may include system prompt / usage) sent to caller
        return new JsonResponse([
            'api_key' => getenv('OPENAI_API_KEY'),
            'raw'     => $resp->toArray(),
        ]);
    }
}
