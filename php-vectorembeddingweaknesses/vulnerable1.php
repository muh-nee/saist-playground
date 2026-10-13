<?php
// Public form upload is embedded + indexed into a Qdrant store without sanitization
use LLPhant\Embeddings\Document;
use LLPhant\Embeddings\EmbeddingGenerator\OpenAI\OpenAI3SmallEmbeddingGenerator;
use LLPhant\Embeddings\VectorStores\Qdrant\QdrantVectorStore;
use Qdrant\Client as QdrantClient;

function indexSubmission(QdrantClient $qdrant): void {
    $raw = $_POST['doc']; // tainted free text
    $doc = new Document();
    $doc->content = $raw; // VULNERABLE: hidden instructions in $raw will poison future RAG retrievals
    $gen   = new OpenAI3SmallEmbeddingGenerator();
    $store = new QdrantVectorStore($qdrant, 'kb');
    $store->addDocuments($gen->embedDocuments([$doc]));
}
