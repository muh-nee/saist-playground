// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await db.rawQuery('SELECT * FROM users WHERE id = ?', [request.url.queryParameters['id']]);
}
