# Marshal.load on user-supplied data — arbitrary object instantiation
require 'base64'

class SessionController < ApplicationController
  def restore
    data = params[:session_data]
    obj = Marshal.load(Base64.decode64(data)) # VULNERABLE — attacker controls object graph
    session[:user] = obj
    render json: { restored: true }
  end

  def load_preferences
    raw = cookies[:prefs]
    prefs = Marshal.load(Base64.decode64(raw)) # VULNERABLE
    render json: prefs
  end
end
