// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  for (final document in documents.take(maxDocuments)) { await limiter.run(() => model.generateContent([Content.text(document)])); }
}
