// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await Process.start(allowedExecutables[tool]!, validatedArgs, runInShell: false);
}
