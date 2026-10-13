<?php
// Author trust check + markup stripping + bounded size before Weaviate upsert
use GuzzleHttp\Client;

const MAX_CHARS = 4_000;

function indexForumPost(PDO $pdo, int $postId): void {
    $stmt = $pdo->prepare(
        'SELECT p.body, u.trust_tier FROM forum_posts p JOIN users u ON u.id = p.author_id WHERE p.id = ?'
    );
    $stmt->execute([$postId]);
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if (!$row || ($row['trust_tier'] ?? 'low') !== 'staff') {
        return; // SAFE: only staff-authored posts are promoted to the shared KB
    }
    $text = strip_tags((string) $row['body']);
    $text = preg_replace('/[\x00-\x08\x0B\x0C\x0E-\x1F\x7F]/u', '', $text) ?? '';
    $text = mb_substr(trim($text), 0, MAX_CHARS);

    $http = new Client(['base_uri' => 'http://weaviate:8080/', 'timeout' => 5]);
    $http->post('/v1/objects', [
        'json' => [
            'class'      => 'KB',
            'properties' => ['content' => $text, 'source' => 'forum', 'author_tier' => 'staff'],
        ],
    ]);
}
