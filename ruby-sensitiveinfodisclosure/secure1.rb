# Only expose non-sensitive fields in API responses
class UsersController < ApplicationController
  def show
    @user = User.find(params[:id])
    # SAFE — explicit allowlist of safe attributes
    render json: @user.slice(:id, :name, :email, :created_at)
  end

  def profile
    # SAFE — use a dedicated serializer that omits sensitive fields
    render json: UserSerializer.new(current_user).as_json
  end
end
