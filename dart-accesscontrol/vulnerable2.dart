// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return repository.findById(request.params['accountId']);
}
