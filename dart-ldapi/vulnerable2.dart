// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await ldap.query(
    baseDn,
    '(|(mail=${await request.readAsString()})(cn=*))',
    ['cn'],
  );
}
