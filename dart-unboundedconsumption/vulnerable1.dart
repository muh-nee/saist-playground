// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await GenerativeModel(model: 'gemini').generateContent([Content.text(await request.readAsString())]);
}
