# User input with embedded newlines written to log — log injection
require 'logger'

logger = Logger.new('/var/log/app.log')

def log_login_attempt(logger, username)
  # VULNERABLE — username may contain \n, allowing fake log entries
  logger.info("Login attempt for user: #{username}")
end

def log_request(logger, ip, path)
  logger.warn("Request from #{ip} to #{path}") # VULNERABLE
end
