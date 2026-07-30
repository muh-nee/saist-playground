# Rails logger with unsanitized params
class SessionsController < ApplicationController
  def create
    username = params[:username]
    # VULNERABLE — attacker can inject newlines to forge log lines
    Rails.logger.info("User logged in: #{username}")
    # ... auth logic ...
  end

  def destroy
    user_agent = request.user_agent
    Rails.logger.debug("Session ended, UA: #{user_agent}") # VULNERABLE
  end
end
