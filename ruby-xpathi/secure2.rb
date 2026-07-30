# Use XPath variable binding (Nokogiri custom XPath function) to avoid injection
require 'nokogiri'

class AuthController < ApplicationController
  def login
    username = params[:username].to_s
    password = params[:password].to_s
    doc      = Nokogiri::XML(File.read('users.xml'))

    # SAFE — iterate and compare in Ruby rather than embedding values into XPath
    user_node = doc.xpath("//user").find do |node|
      node.at_xpath("username")&.text == username &&
        node.at_xpath("password")&.text == password
    end

    if user_node
      session[:user] = username
      render json: { status: 'logged_in' }
    else
      render json: { error: 'invalid' }, status: 401
    end
  end
end
