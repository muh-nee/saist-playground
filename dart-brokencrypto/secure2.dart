// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final box = await Chacha20.poly1305Aead().encrypt(data, secretKey: managedKey, nonce: randomNonce);
}
