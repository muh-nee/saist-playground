// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  for (final entry in ZipDecoder().decodeBytes(bytes)) { final target = p.normalize(p.join(root, entry.name)); if (!p.isWithin(root, target)) throw FormatException('entry'); File(target).writeAsBytesSync(entry.readBytes()!); }
}
