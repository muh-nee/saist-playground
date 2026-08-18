// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await File(await request.readAsString()).writeAsString(payload);
}
