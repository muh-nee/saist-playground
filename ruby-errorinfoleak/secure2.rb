# Generic error codes mapped from exception types
class UsersController < ApplicationController
  def show
    @user = User.find(params[:id])
    render json: @user.slice(:id, :name, :email)
  rescue ActiveRecord::RecordNotFound
    render json: { error: 'not_found' }, status: 404 # SAFE — no internal details
  rescue ActiveRecord::StatementInvalid
    Rails.logger.error("DB error for user #{params[:id]}: #{$!.message}")
    render json: { error: 'server_error' }, status: 500 # SAFE
  end
end
