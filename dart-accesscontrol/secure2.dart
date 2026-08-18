// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return repository.findByIdForOwner(validatedId, authenticatedUser.id);
}
