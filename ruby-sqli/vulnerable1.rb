# String interpolation directly into ActiveRecord query — SQL injection
class UsersController < ApplicationController
  def search
    name = params[:name]
    @users = User.where("name = '#{name}'") # VULNERABLE
    render json: @users
  end

  def find_by_age
    age = params[:age]
    @users = User.where("age > #{age}") # VULNERABLE
    render json: @users
  end
end
