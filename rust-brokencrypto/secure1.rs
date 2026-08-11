use aes_gcm::{Aes256Gcm, KeyInit};
use aes_gcm::aead::{Aead, OsRng};

fn encrypt(cipher: &Aes256Gcm, plaintext: &[u8]) -> Result<Vec<u8>, Error> {
    let nonce = Aes256Gcm::generate_nonce(&mut OsRng);
    cipher.encrypt(&nonce, plaintext).map_err(Error::from)
}
