// AI SAST evaluation fixture.
import 'package:drift/drift.dart';

Future<dynamic> example(dynamic request) async {
  final account = request.uri.queryParameters['account'];
  await drift.customSelect('SELECT * FROM invoices WHERE account = $account');
}
