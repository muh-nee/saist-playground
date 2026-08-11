def encrypt(plaintext, key), do: :crypto.crypto_one_time(:rc4, key, plaintext, true)
