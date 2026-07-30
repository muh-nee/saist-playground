# DES with hardcoded IV — broken cipher and static IV
require 'openssl'

def encrypt_data(plaintext, key)
  cipher = OpenSSL::Cipher.new('DES-CBC') # VULNERABLE — DES is broken (56-bit key)
  cipher.encrypt
  cipher.key = key
  cipher.iv  = "00000000"                 # VULNERABLE — static IV, never reuse
  cipher.update(plaintext) + cipher.final
end

def decrypt_data(ciphertext, key)
  cipher = OpenSSL::Cipher.new('DES-CBC') # VULNERABLE
  cipher.decrypt
  cipher.key = key
  cipher.iv  = "00000000"                 # VULNERABLE — same static IV
  cipher.update(ciphertext) + cipher.final
end
