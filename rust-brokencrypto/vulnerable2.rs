use openssl::symm::Cipher;

fn encrypt(plaintext: &[u8], key: &[u8]) -> Result<Vec<u8>, Error> {
    openssl::symm::encrypt(Cipher::rc4(), key, None, plaintext).map_err(Error::from)
}
