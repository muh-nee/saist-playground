# MD5 used to hash passwords — collision-prone and fast (easy to brute-force)
require 'digest'

def create_user(username, password)
  password_hash = Digest::MD5.hexdigest(password) # VULNERABLE — MD5 is not for passwords
  User.create!(username: username, password_digest: password_hash)
end

def verify_password(user, input)
  Digest::MD5.hexdigest(input) == user.password_digest # VULNERABLE
end
