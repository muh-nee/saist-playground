# All security flags set on session cookie
class SessionsController < ApplicationController
  def create
    token = SecureRandom.hex(32)
    # SAFE — secure, httponly, and samesite all set
    cookies[:session_token] = {
      value:     token,
      secure:    true,      # SAFE — HTTPS only
      httponly:  true,      # SAFE — no JS access
      same_site: :strict    # SAFE — prevents CSRF
    }
    render json: { logged_in: true }
  end
end
