# Parameterized queries with ActiveRecord — safe from SQL injection
class UsersController < ApplicationController
  def search
    name = params[:name]
    @users = User.where(name: name) # SAFE — hash conditions
    render json: @users
  end

  def find_by_age
    age = params[:age].to_i
    @users = User.where("age > ?", age) # SAFE — positional placeholder
    render json: @users
  end
end
