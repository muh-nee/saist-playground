// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await client.files.upload(
    bytes: verifiedDataset.bytes,
    filename: 'training.jsonl',
    purpose: FilePurpose.fineTune,
  );
}
