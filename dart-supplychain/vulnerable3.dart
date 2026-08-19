// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final runtime = Runtime.ofProgram(await Dio().get(remoteProgramUrl));
}
