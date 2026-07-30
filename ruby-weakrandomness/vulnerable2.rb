# srand with predictable seed, then rand for OTP
def generate_otp(user_id)
  srand(user_id)          # VULNERABLE — seed based on predictable value
  rand(100_000..999_999)  # VULNERABLE — attacker who knows user_id can predict OTP
end

def generate_password_reset_token
  characters = ('a'..'z').to_a + ('0'..'9').to_a
  Array.new(16) { characters.sample }.join # VULNERABLE — Array#sample uses Kernel#rand
end
