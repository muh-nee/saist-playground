// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return Response.internalServerError(body: stackTrace.toString());
}
