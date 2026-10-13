<?php
// LLM response is labeled as informational, queued for clinician review, never returned as a diagnosis
use App\Models\TriageDraft;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

class TriageController
{
    private const DISCLAIMER = 'Informational triage only. Not a medical diagnosis. A clinician will review before any care decision.';

    public function __invoke(Request $req): JsonResponse
    {
        $symptoms = (string) $req->request->get('symptoms');
        $client   = \OpenAI::client(getenv('OPENAI_API_KEY'));
        $resp = $client->chat()->create([
            'model' => 'gpt-4o-mini',
            'messages' => [
                ['role' => 'system', 'content' => 'Suggest possible categories only. Never prescribe.'],
                ['role' => 'user',   'content' => $symptoms],
            ],
        ]);

        // SAFE: store as a reviewer draft; the response makes clear it is not a diagnosis
        $draft = TriageDraft::create([
            'symptoms' => $symptoms,
            'ai_notes' => $resp->choices[0]->message->content,
            'status'   => 'awaiting_clinician_review',
        ]);
        return new JsonResponse([
            'draft_id'    => $draft->id,
            'status'      => 'awaiting_clinician_review',
            'disclaimer'  => self::DISCLAIMER,
        ]);
    }
}
