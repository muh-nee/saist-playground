use cookie::{Cookie, SameSite};

fn session_cookie(session_id: String) -> Cookie<'static> {
    Cookie::build(("session", session_id)).secure(true).http_only(true).same_site(SameSite::Lax).path("/").build()
}
