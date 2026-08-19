// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final nodes = document.xpath('//user[name="${request.url.queryParameters['name']}"]');
}
