// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await chroma.similaritySearch(query: await request.readAsString());
}
