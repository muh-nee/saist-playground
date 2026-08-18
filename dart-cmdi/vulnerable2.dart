// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await Process.start('/bin/sh', ['-c', await request.readAsString()]);
}
