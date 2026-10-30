<?php
// Raw LLM diagnosis returned as authoritative medical advice
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

class DiagnosisController
{
    public function __invoke(Request $req): JsonResponse
    {
        $client = \OpenAI::client(getenv('OPENAI_API_KEY'));
        $resp = $client->chat()->create([
            'model' => 'gpt-4o-mini',
            'messages' => [[
                'role' => 'user',
                'content' => 'Patient reports: ' . (string) $req->request->get('symptoms')
                           . '. Give the diagnosis and specific prescription.',
            ]],
        ]);
        // VULNERABLE: ungrounded LLM text returned as a diagnosis — no disclaimer, no clinician review
        return new JsonResponse(['diagnosis' => $resp->choices[0]->message->content]);
    }
}

