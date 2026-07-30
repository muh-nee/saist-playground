# RC4 stream cipher and ECB mode — both insecure
require 'openssl'

def encrypt_with_rc4(data, key)
  cipher = OpenSSL::Cipher.new('RC4') # VULNERABLE — RC4 is broken
  cipher.encrypt
  cipher.key = key
  cipher.update(data) + cipher.final
end

def encrypt_aes_ecb(data, key)
  cipher = OpenSSL::Cipher.new('AES-128-ECB') # VULNERABLE — ECB mode reveals patterns
  cipher.encrypt
  cipher.key = key
  cipher.update(data) + cipher.final
end
