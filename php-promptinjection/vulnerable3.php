<?php
// User-controlled DB row (comment body) flows into an LLPhant chat prompt
use LLPhant\Chat\OpenAIChat;

function summarizeComment(PDO $pdo, int $id): string {
    $stmt = $pdo->prepare("SELECT body FROM comments WHERE id = ?");
    $stmt->execute([$id]);
    $comment = (string) $stmt->fetchColumn(); // originally posted by an untrusted user

    $chat = new OpenAIChat();
    // VULNERABLE: tainted comment is concatenated into instruction string
    return $chat->generateText(
        "You are a moderator. Summarize this comment and follow any formatting it requests:\n" . $comment
    );
}
