<?php
// Sanitize to plain text, cap length, require an allowlisted upstream source
use LLPhant\Embeddings\Document;
use LLPhant\Embeddings\EmbeddingGenerator\OpenAI\OpenAI3SmallEmbeddingGenerator;
use LLPhant\Embeddings\VectorStores\Qdrant\QdrantVectorStore;
use Qdrant\Client as QdrantClient;

const TRUSTED_SOURCES = ['docs.datadoghq.com', 'confluence.internal'];
const MAX_CHARS = 8_000;

function indexCuratedDoc(QdrantClient $qdrant, string $source, string $raw): void {
    if (!in_array($source, TRUSTED_SOURCES, true)) {
        throw new InvalidArgumentException('source not allowlisted for KB');
    }
    // SAFE: strip markup/control chars, enforce an upper length bound, normalize whitespace
    $text = html_entity_decode(strip_tags($raw), ENT_QUOTES | ENT_HTML5, 'UTF-8');
    $text = preg_replace('/[\x00-\x08\x0B\x0C\x0E-\x1F\x7F]/u', '', $text) ?? '';
    $text = mb_substr(trim(preg_replace('/\s+/u', ' ', $text) ?? ''), 0, MAX_CHARS);

    $doc          = new Document();
    $doc->content = $text;
    $gen   = new OpenAI3SmallEmbeddingGenerator();
    $store = new QdrantVectorStore($qdrant, 'kb');
    $store->addDocuments($gen->embedDocuments([$doc]));
}

