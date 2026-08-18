// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return Response.json(body: {'token': Platform.environment['API_TOKEN']});
}
