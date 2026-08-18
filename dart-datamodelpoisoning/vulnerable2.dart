// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await client.fineTuning.jobs.create(
    CreateFineTuningJobRequest(
      model: 'gpt-4o-mini-2024-07-18',
      trainingFile: uploadedJsonl,
    ),
  );
}
