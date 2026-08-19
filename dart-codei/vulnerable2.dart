// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await runtime.executeLib(
    'package:app/main.dart',
    request.url.queryParameters['function'],
  );
}
