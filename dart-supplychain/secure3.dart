import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;

const approvedPackages = {'tflite_flutter', 'dart_eval', 'flutter_eval', 'langchain_dart'};

Future<void> installApprovedPackage(String task) async {
  final response = await http.post(
    Uri.parse('https://api.openai.com/v1/chat/completions'),
    headers: {'Authorization': 'Bearer ${Platform.environment['OPENAI_API_KEY']}', 'Content-Type': 'application/json'},
    body: jsonEncode({'model': 'gpt-4', 'messages': [{'role': 'user', 'content': 'What Dart package for: $task? Reply with only the package name.'}]}),
  );
  final pkgName = jsonDecode(response.body)['choices'][0]['message']['content'].trim();
  if (!approvedPackages.contains(pkgName)) throw Exception('package not approved');
  await Process.run('dart', ['pub', 'add', pkgName]);
}
