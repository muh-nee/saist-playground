// AI SAST evaluation fixture.
import 'package:sqflite/sqflite.dart';

Future<dynamic> example(dynamic request) async {
  await db.rawQuery('SELECT * FROM users WHERE id = ${request.url.queryParameters['id']}');
}
