use aes_gcm::{Aes256Gcm, Nonce};

fn encrypt(cipher: &Aes256Gcm, plaintext: &[u8]) -> Result<Vec<u8>, Error> {
    let nonce = Nonce::from_slice(b"fixed nonce!");
    cipher.encrypt(nonce, plaintext).map_err(Error::from)
}
