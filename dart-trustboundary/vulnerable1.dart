// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  request.session['role'] = request.url.queryParameters['role'];
}
