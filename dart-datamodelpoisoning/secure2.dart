// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await trainer.addDataset(await reviewAndVerify(uploadedDataset));
}
