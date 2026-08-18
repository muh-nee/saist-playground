// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  element.innerHtml = await request.readAsString();
}
