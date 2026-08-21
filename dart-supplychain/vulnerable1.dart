import 'package:http/http.dart' as http;
import 'package:tflite_flutter/tflite_flutter.dart';

Future<dynamic> loadModel(String modelUrl) async {
  final response = await http.get(Uri.parse(modelUrl));
  final interpreter = Interpreter.fromBuffer(response.bodyBytes);
  return interpreter;
}
