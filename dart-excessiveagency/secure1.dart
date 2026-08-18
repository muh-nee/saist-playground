// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final tool = Tool.fromFunction(
    name: 'readStatus',
    description: 'Read status after validating access',
    inputJsonSchema: {
      'type': 'object',
      'properties': {
        'id': {'type': 'string'},
      },
      'required': ['id'],
    },
    func: (input) => repository.readAuthorizedStatus(
      user,
      validateAuthorizedId(user, input['id'] as String),
    ),
  );
}
