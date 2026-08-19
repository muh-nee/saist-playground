// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final draft = await chatOpenAI.invoke(legalQuestion); return Response.ok({'draft': draft.content, 'disclaimer': disclaimer});
}
