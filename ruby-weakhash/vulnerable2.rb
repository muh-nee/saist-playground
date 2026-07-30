# SHA-1 for password storage — broken for cryptographic purposes
require 'digest'

def hash_password(password)
  Digest::SHA1.hexdigest(password) # VULNERABLE — SHA-1 is broken, no salt
end

def hash_with_salt(password, salt)
  Digest::SHA1.hexdigest(salt + password) # VULNERABLE — SHA-1 still inappropriate
end

def store_token(secret)
  Digest::SHA256.hexdigest(secret) # VULNERABLE — SHA-256 is too fast for secrets
end
