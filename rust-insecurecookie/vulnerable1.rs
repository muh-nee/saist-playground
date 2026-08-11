use cookie::Cookie;

fn session_cookie(session_id: String) -> Cookie<'static> {
    Cookie::build(("session", session_id)).path("/").build()
}
