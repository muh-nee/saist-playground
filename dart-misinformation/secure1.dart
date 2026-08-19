// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final answer = await model.generateContent(medicalPrompt); return Response.ok(await clinicianReview(groundAndCite(answer.text)));
}
