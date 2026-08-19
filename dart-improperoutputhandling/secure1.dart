// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final action = ActionSchema.parse((await model.generateContent(prompt)).text); await approvedActions[action.name]!(action.validatedArgs);
}
