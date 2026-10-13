<?php
// Ungrounded LLM legal explanation returned to users as authoritative counsel
use LLPhant\Chat\OpenAIChat;
use Symfony\Component\HttpFoundation\JsonResponse;

class LegalAnswerController
{
    public function __invoke(string $question): JsonResponse
    {
        $chat = new OpenAIChat();
        $answer = $chat->generateText(
            "Explain the legal consequences in California for: {$question}. Be definitive."
        );
        // VULNERABLE: hallucinated statutes/cases returned without citations or attorney review
        return new JsonResponse(['legal_answer' => $answer]);
    }
}
