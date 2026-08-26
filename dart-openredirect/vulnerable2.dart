// AI SAST evaluation example fixture.
Future<dynamic> example(dynamic request) async {
  await response.redirect(Uri.parse(await request.readAsString()));
}
