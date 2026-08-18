// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final archive = TarDecoder().decodeBytes(bytes);
  for (final entry in archive) {
    File(p.join(outputDir, entry.name)).writeAsBytesSync(entry.readBytes()!);
  }
}
