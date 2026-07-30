# bcrypt — adaptive hash function designed for passwords
require 'bcrypt'

def create_user(username, password)
  # SAFE — BCrypt automatically generates a salt and uses work factor
  password_hash = BCrypt::Password.create(password, cost: 12)
  User.create!(username: username, password_digest: password_hash)
end

def verify_password(user, input)
  # SAFE — constant-time comparison built into BCrypt::Password
  BCrypt::Password.new(user.password_digest) == input
end
