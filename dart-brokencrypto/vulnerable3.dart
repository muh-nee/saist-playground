// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final cipher = RC4Engine()..init(true, KeyParameter(Key.fromUtf8('hardcoded').bytes));
}
