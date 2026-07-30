# Strong parameters prevent mass assignment; explicit ownership check
class UsersController < ApplicationController
  before_action :require_admin, only: [:update]

  def update
    @user = User.find(params[:id])
    # SAFE — only permit safe attributes, never :role via this action
    @user.update!(user_params)
    render json: @user.slice(:id, :name, :email)
  end

  def show
    # SAFE — always use current_user, ignore user_id param for non-admins
    @user = current_user
    render json: @user.slice(:id, :name, :email)
  end

  private

  def user_params
    params.require(:user).permit(:name, :email) # SAFE — role excluded
  end
end
