import 'package:dart_eval/dart_eval.dart';
import 'package:dio/dio.dart';

Future<dynamic> loadRemoteProgram(String remoteProgramUrl) async {
  final response = await Dio().get(remoteProgramUrl);
  final runtime = Runtime.ofProgram(response.data);
  return runtime;
}
