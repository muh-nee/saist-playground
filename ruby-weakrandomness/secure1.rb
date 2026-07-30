# SecureRandom — cryptographically secure random generation
class SessionsController < ApplicationController
  def create
    token = SecureRandom.hex(32) # SAFE — 256 bits of cryptographically secure randomness
    session[:token] = token
    render json: { token: token }
  end

  def reset_token
    new_token = SecureRandom.urlsafe_base64(32) # SAFE
    current_user.update!(api_token: new_token)
    render json: { token: new_token }
  end
end
