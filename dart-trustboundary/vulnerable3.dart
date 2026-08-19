// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  request.context['isAdmin'] = request.headers['x-admin'];
}
