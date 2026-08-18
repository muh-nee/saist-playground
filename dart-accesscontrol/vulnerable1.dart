// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return firestore.collection('files').doc(request.url.queryParameters['id']).get();
}
