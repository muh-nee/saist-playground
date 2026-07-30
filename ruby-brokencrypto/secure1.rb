# AES-256-GCM with random IV and authenticated encryption
require 'openssl'
require 'base64'

def encrypt_data(plaintext, key)
  cipher = OpenSSL::Cipher.new('AES-256-GCM') # SAFE — strong authenticated cipher
  cipher.encrypt
  cipher.key = key
  iv  = cipher.random_iv # SAFE — fresh random IV each time
  tag = cipher.auth_tag
  ciphertext = cipher.update(plaintext) + cipher.final
  { iv: Base64.strict_encode64(iv), tag: Base64.strict_encode64(tag),
    ct: Base64.strict_encode64(ciphertext) }
end

def decrypt_data(payload, key)
  cipher = OpenSSL::Cipher.new('AES-256-GCM') # SAFE
  cipher.decrypt
  cipher.key      = key
  cipher.iv       = Base64.strict_decode64(payload[:iv])
  cipher.auth_tag = Base64.strict_decode64(payload[:tag])
  cipher.update(Base64.strict_decode64(payload[:ct])) + cipher.final
end
