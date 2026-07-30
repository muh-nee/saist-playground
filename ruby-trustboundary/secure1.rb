# Role loaded from the database after authentication — not from params
class SessionsController < ApplicationController
  def create
    user = User.find_by(email: params[:email])
    return render json: { error: 'unauthorized' }, status: 401 unless user&.authenticate(params[:password])

    # SAFE — role is fetched from the trusted database record, not from user input
    session[:user_id] = user.id
    session[:role]    = user.role
    render json: { logged_in: true }
  end
end

class AdminController < ApplicationController
  def dashboard
    user = User.find(session[:user_id])
    raise "Forbidden" unless user.role == 'admin' # SAFE — sourced from DB
    render json: { admin: true }
  end
end
