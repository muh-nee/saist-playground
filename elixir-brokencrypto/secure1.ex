def encrypt(plaintext, key, nonce), do: :crypto.crypto_one_time_aead(:aes_256_gcm, key, nonce, plaintext, <<>>, true)
