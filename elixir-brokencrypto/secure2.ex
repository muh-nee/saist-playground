def encrypt(plaintext, key) do
  nonce = :crypto.strong_rand_bytes(12)
  {ciphertext, tag} = :crypto.crypto_one_time_aead(:chacha20_poly1305, key, nonce, plaintext, <<>>, true)
  {nonce, ciphertext, tag}
end
