// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  response.write(SystemChatMessage(developerInstructions).content);
}
