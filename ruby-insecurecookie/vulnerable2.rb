# Cookie with long expiry and no SameSite — CSRF and theft risk
class AuthController < ApplicationController
  def login
    token = generate_token(current_user)
    # VULNERABLE — expires too long, no httponly, no samesite
    cookies[:auth_token] = {
      value:   token,
      expires: 10.years.from_now,
      secure:  false # VULNERABLE — transmitted over HTTP
    }
    render json: { ok: true }
  end

  private

  def generate_token(user)
    SecureRandom.hex(32)
  end
end
