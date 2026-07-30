# Unsalted SHA-512 for passwords — fast hash, rainbow table vulnerable
require 'digest'

class UsersController < ApplicationController
  def create
    pw_hash = Digest::SHA512.hexdigest(params[:password]) # VULNERABLE — no KDF, no salt
    @user = User.create!(email: params[:email], password_hash: pw_hash)
    render json: { id: @user.id }
  end
end
