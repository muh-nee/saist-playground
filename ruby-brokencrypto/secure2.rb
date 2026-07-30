# Use the RbNaCl / libsodium secretbox — modern, high-level crypto
require 'rbnacl'

def encrypt_message(plaintext, key_bytes)
  box   = RbNaCl::SecretBox.new(key_bytes)
  nonce = RbNaCl::Random.random_bytes(box.nonce_bytes) # SAFE — random nonce
  ciphertext = box.box(nonce, plaintext)               # SAFE — authenticated encryption
  { nonce: nonce, ciphertext: ciphertext }
end

def decrypt_message(nonce, ciphertext, key_bytes)
  box = RbNaCl::SecretBox.new(key_bytes)
  box.open(nonce, ciphertext) # SAFE — verifies authentication tag
end
