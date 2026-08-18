// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final bytes = await download(pinnedUrl); verifySha256(bytes, pinnedDigest); final interpreter = Interpreter.fromBuffer(bytes);
}
