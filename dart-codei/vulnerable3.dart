// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await webView.runJavaScript(externalMessage);
}
