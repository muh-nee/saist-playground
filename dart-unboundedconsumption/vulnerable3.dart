// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await Future.wait(documents.map((d) => model.generateContent([Content.text(d)])));
}
