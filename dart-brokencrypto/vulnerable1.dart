// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final cipher = ECBBlockCipher(AESEngine())..init(true, KeyParameter(key));
}
