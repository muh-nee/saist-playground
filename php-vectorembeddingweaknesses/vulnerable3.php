<?php
// Untrusted DB row (user forum post) added to a Weaviate collection used for RAG grounding
use GuzzleHttp\Client;

function indexForumPost(PDO $pdo, int $postId): void {
    $stmt = $pdo->prepare('SELECT body FROM forum_posts WHERE id = ?');
    $stmt->execute([$postId]);
    $body = (string) $stmt->fetchColumn(); // written by any registered user

    $http = new Client(['base_uri' => 'http://weaviate:8080/']);
    // VULNERABLE: no length cap, no sanitization, no author trust tier
    $http->post('/v1/objects', [
        'json' => [
            'class'      => 'KB',
            'properties' => ['content' => $body, 'source' => 'forum'],
        ],
    ]);
}

