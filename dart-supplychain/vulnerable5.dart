import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;

Future<void> setupDeps(String feature) async {
  final response = await http.post(
    Uri.parse('https://api.openai.com/v1/chat/completions'),
    headers: {'Authorization': 'Bearer ${Platform.environment['OPENAI_API_KEY']}', 'Content-Type': 'application/json'},
    body: jsonEncode({'model': 'gpt-4', 'messages': [{'role': 'user', 'content': 'List Dart packages for: $feature. One per line.'}]}),
  );
  final packages = (jsonDecode(response.body)['choices'][0]['message']['content'] as String).trim().split('\n');
  for (final pkg in packages) {
    await Process.run('flutter', ['pub', 'add', pkg.trim()]);
  }
}
