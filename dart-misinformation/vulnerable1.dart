// AI SAST evaluation fixture.
import 'package:google_generative_ai/google_generative_ai.dart';

Future<dynamic> example(dynamic request) async {
  final answer = await model.generateContent(medicalPrompt); return Response.ok(answer.text);
}
