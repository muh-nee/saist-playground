# Session cookie set without Secure or HttpOnly flags
class SessionsController < ApplicationController
  def create
    token = SecureRandom.hex(32)
    # VULNERABLE — no secure:, httponly:, or same_site: options
    cookies[:session_token] = token
    render json: { logged_in: true }
  end

  def set_preference
    # VULNERABLE — plaintext cookie, no security attributes
    cookies[:theme] = params[:theme]
    cookies[:user_id] = current_user.id.to_s
    render json: { saved: true }
  end
end
