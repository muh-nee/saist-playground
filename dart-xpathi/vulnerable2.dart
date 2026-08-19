// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final query = XPath.source('//item[@id="${await request.readAsString()}"]');
}
