// AI SAST evaluation fixture.
import 'package:google_generative_ai/google_generative_ai.dart';

Future<dynamic> example(dynamic request) async {
  await model.generateContent([Content.text(await request.readAsString())]);
}
