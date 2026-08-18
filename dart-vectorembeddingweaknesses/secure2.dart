// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await store.similaritySearch(
    query: validatedQuery,
    config: VectorStoreSimilaritySearch(
      filter: {'ownerId': authenticatedUser.id},
    ),
  );
}
