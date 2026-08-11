def encrypt(plaintext, key), do: :crypto.crypto_one_time(:aes_256_ecb, key, plaintext, true)
