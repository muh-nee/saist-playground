// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await Tflite.loadModel(model: request.url.queryParameters['model']);
}
