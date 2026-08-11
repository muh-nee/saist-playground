use sha1::{Digest, Sha1};

fn signing_digest(payload: &[u8]) -> Vec<u8> {
    Sha1::digest(payload).to_vec()
}
