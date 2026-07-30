# has_secure_password (Rails) uses bcrypt under the hood
class User < ApplicationRecord
  has_secure_password # SAFE — uses bcrypt with automatic salting

  validates :email, presence: true, uniqueness: true
end

class SessionsController < ApplicationController
  def create
    user = User.find_by(email: params[:email])
    # SAFE — authenticate! uses bcrypt's built-in comparison
    if user&.authenticate(params[:password])
      session[:user_id] = user.id
      render json: { status: 'logged_in' }
    else
      render json: { error: 'invalid credentials' }, status: :unauthorized
    end
  end
end
