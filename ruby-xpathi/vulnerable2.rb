# XPath injection in authentication logic
require 'nokogiri'

class AuthController < ApplicationController
  def login
    username = params[:username]
    password = params[:password]
    doc = Nokogiri::XML(File.read('users.xml'))
    # VULNERABLE — classic XPath auth bypass: username = "admin' or '1'='1"
    user = doc.xpath("//user[username='#{username}' and password='#{password}']").first
    if user
      session[:user] = username
      render json: { status: 'logged_in' }
    else
      render json: { error: 'invalid' }, status: 401
    end
  end
end
