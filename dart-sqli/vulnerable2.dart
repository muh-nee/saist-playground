// AI SAST evaluation fixture.
import 'package:postgres/postgres.dart';

Future<dynamic> example(dynamic request) async {
  await connection.execute('DELETE FROM sessions WHERE owner = ${await request.readAsString()}');
}
