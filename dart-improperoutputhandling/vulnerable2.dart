// AI SAST evaluation fixture.
import 'package:langchain/langchain.dart';

Future<dynamic> example(dynamic request) async {
  final answer = await chatOpenAI.invoke(prompt); await db.rawQuery(answer.content);
}
