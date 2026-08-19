// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  element.text = request.url.queryParameters['text'];
}
