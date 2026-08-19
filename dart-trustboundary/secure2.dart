// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final claims = JwtClaim(otherClaims: {'tenant': authenticatedUser.tenantId});
}
