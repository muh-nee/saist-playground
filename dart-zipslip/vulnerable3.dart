// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  for (final ArchiveFile entry in archive) {
    File(p.join(root, entry.name)).writeAsBytesSync(entry.readBytes()!);
  }
}
