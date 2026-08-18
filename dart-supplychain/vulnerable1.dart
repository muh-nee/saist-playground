// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final interpreter = Interpreter.fromBuffer((await http.get(Uri.parse(modelUrl))).bodyBytes);
}
