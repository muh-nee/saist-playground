fn encrypt(cipher: ring::aead::LessSafeKey, nonce: ring::aead::Nonce, data: &mut Vec<u8>) -> Result<(), Error> {
    cipher.seal_in_place_append_tag(nonce, ring::aead::Aad::empty(), data).map_err(Error::from)
}
