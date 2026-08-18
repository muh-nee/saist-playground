// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await pinecone.similaritySearch(
    query: query,
    config: VectorStoreSimilaritySearch(
      filter: {'tenantId': user.tenantId, 'visibility': 'allowed'},
    ),
  );
}
