// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final action = await model.generateContent([Content.text(validatedStructuredInput)]); await enforcePolicy(ActionSchema.parse(action.text));
}
