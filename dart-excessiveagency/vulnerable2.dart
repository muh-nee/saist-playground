// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final tool = Tool.fromFunction(
    name: 'shell',
    description: 'Run a shell command',
    inputJsonSchema: {
      'type': 'object',
      'properties': {
        'command': {'type': 'string'},
      },
      'required': ['command'],
    },
    func: (input) => Process.run(
      input['command'] as String,
      const [],
      runInShell: true,
    ),
  );
}
