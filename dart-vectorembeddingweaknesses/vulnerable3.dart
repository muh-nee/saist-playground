// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await MemoryVectorStore(
    embeddings: embeddings,
  ).similaritySearch(query: externalQuery);
}
