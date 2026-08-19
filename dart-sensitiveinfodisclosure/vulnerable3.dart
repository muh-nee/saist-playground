// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  await model.generateContent([Content.text('secret=${dotenv.env['SECRET']}')]);
}
