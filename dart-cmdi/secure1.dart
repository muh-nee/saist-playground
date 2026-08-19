// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await Process.run('/usr/bin/git', ['show', allowedRevision]);
}
