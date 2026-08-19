// AI SAST evaluation fixture.
Future<dynamic> example(dynamic request) async {
  return supabase.from('invoices').delete().eq('id', await request.readAsString());
}
