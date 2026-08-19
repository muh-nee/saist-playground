// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final target = Uri.parse(next); if (target.host != 'example.com' || target.scheme != 'https') throw ArgumentError('redirect'); await response.redirect(target);
}
