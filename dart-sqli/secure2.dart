// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await (database.users.select()..where((u) => u.id.equals(validatedId))).get();
}
