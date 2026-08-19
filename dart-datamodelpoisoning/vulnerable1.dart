// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await client.files.upload(
    bytes: utf8.encode(await request.readAsString()),
    filename: 'training.jsonl',
    purpose: FilePurpose.fineTune,
  );
}
