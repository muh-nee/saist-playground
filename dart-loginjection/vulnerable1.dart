// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  Logger('api').warning('agent=${request.headers['user-agent']}');
}
