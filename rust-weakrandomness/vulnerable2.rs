use rand::{rngs::SmallRng, RngCore, SeedableRng};

fn session_id() -> [u8; 32] {
    let mut token = [0; 32];
    SmallRng::seed_from_u64(42).fill_bytes(&mut token);
    token
}
