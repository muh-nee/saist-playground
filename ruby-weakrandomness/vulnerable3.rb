# Time-seeded random number for CSRF token
class ApplicationController < ActionController::Base
  def generate_csrf_token
    srand(Time.now.to_i) # VULNERABLE — seed from current timestamp is guessable
    rand(2**32).to_s(16)
  end
end
