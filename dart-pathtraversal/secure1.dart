// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final target = p.normalize(p.join(root, requested)); if (!p.isWithin(root, target)) throw ArgumentError('path'); await File(target).readAsString();
}
