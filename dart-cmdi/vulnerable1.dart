// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await Process.run(request.url.queryParameters['cmd'], [], runInShell: true);
}
