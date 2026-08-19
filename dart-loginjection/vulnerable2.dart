// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  developer.log(await request.readAsString());
}
