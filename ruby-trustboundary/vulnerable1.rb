# Storing user-controlled role in the session — trust boundary violation
class SessionsController < ApplicationController
  def create
    # VULNERABLE — role comes from user-controlled params, not from the database
    session[:role] = params[:role]
    session[:user_id] = params[:user_id]
    render json: { logged_in: true }
  end
end

class AdminController < ApplicationController
  def dashboard
    # VULNERABLE — authorization decision based on attacker-controlled session value
    raise "Forbidden" unless session[:role] == 'admin'
    render json: { admin: true }
  end
end
