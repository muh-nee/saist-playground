// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final box = await AesGcm.with256bits().encrypt(data, secretKey: key, nonce: secureNonce);
}
