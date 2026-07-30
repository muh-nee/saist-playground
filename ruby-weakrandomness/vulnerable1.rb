# Kernel#rand used for security-sensitive token generation
class SessionsController < ApplicationController
  def create
    token = rand(10**32).to_s # VULNERABLE — Kernel#rand is not cryptographically secure
    session[:token] = token
    render json: { token: token }
  end

  def reset_token
    new_token = rand.to_s.delete('.') # VULNERABLE — predictable PRNG
    current_user.update!(api_token: new_token)
    render json: { token: new_token }
  end
end
