# Role elevation via mass assignment
class UsersController < ApplicationController
  def update
    @user = User.find(params[:id])
    # VULNERABLE — params may include role: 'admin'
    @user.update(params[:user])
    render json: @user
  end

  def show
    user_id = params[:user_id] || current_user.id
    @user = User.find(user_id) # VULNERABLE — user_id not validated against current_user
    render json: @user
  end
end
