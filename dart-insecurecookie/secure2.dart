// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  response.headers.add('set-cookie', 'auth=$token; Secure; HttpOnly; SameSite=Lax');
}
