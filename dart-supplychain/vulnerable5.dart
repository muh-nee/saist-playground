import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:tflite_flutter/tflite_flutter.dart';

Future<void> setupAndLoadModel(String feature, String modelUrl) async {
  final aiResponse = await http.post(
    Uri.parse('https://api.openai.com/v1/chat/completions'),
    headers: {'Authorization': 'Bearer ${Platform.environment['OPENAI_API_KEY']}', 'Content-Type': 'application/json'},
    body: jsonEncode({'model': 'gpt-4', 'messages': [{'role': 'user', 'content': 'List Dart packages for: $feature. One per line.'}]}),
  );
  final packages = (jsonDecode(aiResponse.body)['choices'][0]['message']['content'] as String).trim().split('\n');
  for (final pkg in packages) {
    await Process.run('flutter', ['pub', 'add', pkg.trim()]);
  }

  final modelResponse = await http.get(Uri.parse(modelUrl));
  final interpreter = Interpreter.fromBuffer(modelResponse.bodyBytes);
}
