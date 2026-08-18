// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return virtualDirectory.serveFile(request.url.queryParameters['path']);
}
