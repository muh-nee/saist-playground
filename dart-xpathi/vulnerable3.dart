// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final result = queryXPath('//account[$externalPredicate]');
}
