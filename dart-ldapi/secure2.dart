// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await ldap.query(baseDn, allowedFilters[filterId]!, ['cn']);
}
