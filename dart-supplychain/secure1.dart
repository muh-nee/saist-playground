// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final bytes = await rootBundle.load('assets/model.tflite'); final interpreter = Interpreter.fromBuffer(bytes.buffer.asUint8List());
}
