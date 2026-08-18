// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await ldap.search(baseDn, Filter.equals('uid', validatedUser), ['cn']);
}
