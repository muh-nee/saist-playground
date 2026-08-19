// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  final match = document.xpath('//user').where((n) => n.getElement('name')?.innerText == validatedName);
}
