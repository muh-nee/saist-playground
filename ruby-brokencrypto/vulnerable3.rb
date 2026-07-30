# 3DES with hardcoded key — weak and deprecated
require 'openssl'
require 'base64'

HARDCODED_KEY = "0123456789abcdef01234567" # VULNERABLE — hardcoded key

def encrypt(plaintext)
  cipher = OpenSSL::Cipher.new('DES-EDE3-CBC') # VULNERABLE — 3DES is deprecated
  cipher.encrypt
  cipher.key = HARDCODED_KEY
  cipher.iv  = cipher.random_iv
  Base64.strict_encode64(cipher.update(plaintext) + cipher.final)
end
