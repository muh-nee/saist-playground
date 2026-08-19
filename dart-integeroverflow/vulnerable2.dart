// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final values = Int32List.fromList([int.parse(await request.readAsString())]);
}
