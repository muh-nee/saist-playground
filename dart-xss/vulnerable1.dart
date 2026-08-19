// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  element.setInnerHtml(request.url.queryParameters['html'], treeSanitizer: NodeTreeSanitizer.trusted);
}
