// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  Logger('ai').info(Content.system(systemPrompt));
}
