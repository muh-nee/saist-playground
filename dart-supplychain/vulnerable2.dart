import 'package:tflite_flutter/tflite_flutter.dart';

Future<dynamic> loadUserModel(dynamic request) async {
  final modelPath = request.url.queryParameters['model'];
  await Tflite.loadModel(model: modelPath);
}
