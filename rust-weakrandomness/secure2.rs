use rand::RngCore;

fn session_id() -> [u8; 32] {
    let mut token = [0; 32];
    rand::rngs::OsRng.fill_bytes(&mut token);
    token
}
