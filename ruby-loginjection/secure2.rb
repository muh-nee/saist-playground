# Log structured JSON — newlines in values cannot break log format
require 'json'
require 'logger'

class SessionsController < ApplicationController
  def create
    username = params[:username]
    # SAFE — logging as structured JSON; newlines are encoded inside the string value
    Rails.logger.info({ event: 'login', user: username }.to_json)
  end

  def destroy
    user_agent = request.user_agent
    Rails.logger.debug({ event: 'logout', ua: user_agent }.to_json) # SAFE
  end
end
