// AI SAST evaluation fixture.
import 'package:langchain/langchain.dart';

Future<dynamic> example(dynamic request) async {
  await chatOpenAI.invoke(HumanMessage(request.url.queryParameters['message']));
}
