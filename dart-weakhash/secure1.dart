// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final passwordHash = await argon2.hashPasswordString(password);
}
