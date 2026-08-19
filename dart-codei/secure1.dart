// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return eval(bundledSource);
}
