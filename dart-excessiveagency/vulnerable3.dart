// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final tool = Tool.function(
    name: 'closeAccount',
    description: 'Close any account by ID',
    parameters: {
      'type': 'object',
      'properties': {
        'id': {'type': 'string'},
      },
      'required': ['id'],
    },
  );
  for (final toolCall in response.allToolCalls) {
    if (toolCall.function.name == 'closeAccount') {
      final id = toolCall.function.arguments['id'] as String;
      await api.delete('/accounts/$id');
    }
  }
}
