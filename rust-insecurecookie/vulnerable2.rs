use cookie::Cookie;

fn auth_cookie(token: String) -> Cookie<'static> {
    Cookie::build(("auth_token", token)).secure(true).path("/").build()
}
