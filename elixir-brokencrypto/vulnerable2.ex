def encrypt(plaintext, key), do: :crypto.crypto_one_time(:des_ecb, key, plaintext, true)
