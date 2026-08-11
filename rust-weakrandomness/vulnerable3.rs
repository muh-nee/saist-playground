use rand::{rngs::StdRng, RngCore, SeedableRng};

fn nonce() -> [u8; 12] {
    let mut nonce = [0; 12];
    StdRng::seed_from_u64(1).fill_bytes(&mut nonce);
    nonce
}
