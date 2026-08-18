// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final value = int.parse(request.url.queryParameters['n']).toUnsigned(16);
}
