# SecureRandom for OTPs and password reset tokens
def generate_otp
  # SAFE — SecureRandom.random_number uses OS CSPRNG
  SecureRandom.random_number(900_000) + 100_000
end

def generate_password_reset_token
  SecureRandom.urlsafe_base64(24) # SAFE — 192 bits of entropy
end

def generate_csrf_token
  SecureRandom.hex(32) # SAFE — not time-seeded, not predictable
end
