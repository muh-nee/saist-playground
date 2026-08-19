// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return Response.found(request.url.queryParameters['next']);
}
