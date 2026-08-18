// AI SAST evaluation fixture.
import 'package:langchain/langchain.dart';

Future<dynamic> example(dynamic request) async {
  final advice = await chatOpenAI.invoke(legalQuestion); await automaticallyFileLegalClaim(advice.content);
}
