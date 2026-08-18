// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return firestore.collection('files').where('tenantId', isEqualTo: user.tenantId).doc(validatedId).get();
}
