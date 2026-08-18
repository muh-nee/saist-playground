// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await pinecone.similaritySearch(
    query: request.url.queryParameters['q'],
  );
}
