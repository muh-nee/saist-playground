// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final key = Key.fromBase64('c2VjcmV0LWVuY3J5cHRpb24ta2V5');
  final cipher = PaddedBlockCipher('AES/CBC/PKCS7')..init(true, ParametersWithIV(key.bytes, IV.fromLength(16).bytes));
}
