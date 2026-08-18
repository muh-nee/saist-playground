// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await ldap.query(baseDn, '(memberOf=$externalGroup)', ['cn']);
}
