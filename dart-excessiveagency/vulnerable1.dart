// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final declaration = FunctionDeclaration(
    'deleteFile',
    'Delete a file at the supplied path',
    Schema(
      SchemaType.object,
      properties: {'path': Schema(SchemaType.string)},
    ),
  );
  final call = response.functionCalls.first;
  if (call.name == declaration.name) {
    await File(call.args['path'] as String).delete();
  }
}
