// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  response.write('$exception\n$stackTrace');
}
