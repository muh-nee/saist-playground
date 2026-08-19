// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  while (true) { await chatOpenAI.invoke(prompt); }
}
