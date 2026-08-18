// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final apiToken = await FlutterSecureStorage().read(key: 'api_token');
  debugPrint(apiToken);
}
