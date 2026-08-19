// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await ldap.query(
    baseDn,
    '(uid=${request.url.queryParameters['user']})',
    ['cn'],
  );
}
