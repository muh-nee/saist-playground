// AI SAST evaluation fixture.
import 'package:google_generative_ai/google_generative_ai.dart';

Future<dynamic> example(dynamic request) async {
  final decision = await model.generateContent(creditApplication); return eligibilityDecision(decision.text);
}
