fn set_session(session_id: &str) -> HeaderValue {
    HeaderValue::from_str(&format!("session={session_id}; Path=/")).unwrap()
}
