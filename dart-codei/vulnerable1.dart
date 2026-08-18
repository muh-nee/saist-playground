// AI SAST evaluation fixture.
import 'package:dart_eval/dart_eval.dart';

Future<dynamic> example(dynamic request) async {
  return eval(await request.readAsString());
}
