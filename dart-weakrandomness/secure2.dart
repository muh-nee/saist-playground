// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final bytes = Uint8List.fromList(List.generate(32, (_) => Random.secure().nextInt(256)));
}
