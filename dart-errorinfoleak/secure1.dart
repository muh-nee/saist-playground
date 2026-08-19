// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  logger.severe('request failed', error, stackTrace); return Response.internalServerError(body: 'request failed');
}
