<?php
// Grounded answer: pass vetted sources into the prompt and require citations; refuse if none ground the claim
use LLPhant\Chat\OpenAIChat;
use Symfony\Component\HttpFoundation\JsonResponse;

class LegalAnswerController
{
    private const DISCLAIMER = 'General information drawn from the cited statutes. Not legal advice; consult an attorney.';

    /**
     * @param array<int, array{title: string, cite: string, text: string}> $vettedSources
     */
    public function answer(string $question, array $vettedSources): JsonResponse
    {
        if ($vettedSources === []) {
            return new JsonResponse(['error' => 'no vetted sources to ground the answer'], 422);
        }

        $context = '';
        foreach ($vettedSources as $s) {
            $context .= "[{$s['cite']}] {$s['title']}\n{$s['text']}\n\n";
        }

        $chat = new OpenAIChat();
        $answer = $chat->generateText(
            "Answer using ONLY the sources below. Cite each claim as [cite].\n"
            . "If the sources don't cover it, reply 'insufficient sources'.\n\n{$context}\nQuestion: {$question}"
        );

        return new JsonResponse([
            'answer'      => $answer,
            'sources'     => array_map(static fn ($s) => $s['cite'], $vettedSources),
            'disclaimer'  => self::DISCLAIMER,
        ]);
    }
}
