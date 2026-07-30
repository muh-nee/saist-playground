# Leaking user PII and internal model fields in JSON serialization
class UsersController < ApplicationController
  def show
    @user = User.find(params[:id])
    render json: @user # VULNERABLE — includes password_digest, api_key, ssn, etc.
  end

  def profile
    @user = current_user
    render json: @user.attributes # VULNERABLE — all database columns exposed
  end
end
