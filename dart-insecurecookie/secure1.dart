// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  response.cookies.add(Cookie('session', token)..secure = true..httpOnly = true..sameSite = SameSite.strict);
}
