// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await File(request.url.queryParameters['file']).readAsString();
}
