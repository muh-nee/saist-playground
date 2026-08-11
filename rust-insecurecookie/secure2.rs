use cookie::{Cookie, SameSite};

fn csrf_cookie(token: String) -> Cookie<'static> {
    Cookie::build(("csrf", token)).secure(true).http_only(true).same_site(SameSite::Strict).path("/").build()
}
