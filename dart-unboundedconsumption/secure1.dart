// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await limitedModel.generateContent([Content.text(body.substring(0, maxInputChars))]).timeout(timeout);
}
