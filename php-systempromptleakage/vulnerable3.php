<?php
// Debug endpoint serializes the system prompt into its JSON response
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

class ChatDebugController
{
    private const SYSTEM_PROMPT = "You are the Datadog billing LLM. Internal rule: apply SKU override token 'OVR-9981-INT'.";

    public function __invoke(Request $req): JsonResponse
    {
        $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
        $resp = $client->chat()->create([
            'model' => 'gpt-4o-mini',
            'messages' => [
                ['role' => 'system', 'content' => self::SYSTEM_PROMPT],
                ['role' => 'user',   'content' => (string) $req->request->get('msg')],
            ],
        ]);
        // VULNERABLE: ships SYSTEM_PROMPT (and model config) to any client calling this endpoint
        return new JsonResponse([
            'answer'        => $resp->choices[0]->message->content,
            'system_prompt' => self::SYSTEM_PROMPT,
            'model'         => 'gpt-4o-mini',
        ]);
    }
}

