// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await Process.runSync(userExecutable, request.url.queryParametersAll['arg'] ?? []);
}
