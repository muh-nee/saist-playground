// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final text = (await model.generateContent(prompt)).text; element.text = text;
}
