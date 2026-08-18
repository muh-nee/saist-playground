// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final model = GenerativeModel(model: 'gemini', systemInstruction: Content.system(systemPrompt));
}
