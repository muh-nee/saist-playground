# Database error details leaked in JSON response
class UsersController < ApplicationController
  def show
    @user = User.find(params[:id])
    render json: @user
  rescue ActiveRecord::RecordNotFound => e
    render json: { error: e.message }, status: 404 # VULNERABLE — reveals table/column names
  rescue ActiveRecord::StatementInvalid => e
    render json: { sql_error: e.message }, status: 500 # VULNERABLE — leaks SQL details
  end
end
