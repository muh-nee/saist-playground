# Strip CR/LF from user-supplied values before logging
require 'logger'

logger = Logger.new('/var/log/app.log')

def sanitize_log(value)
  value.to_s.gsub(/[\r\n]/, ' ') # SAFE — collapse newlines to a space
end

def log_login_attempt(logger, username)
  safe_name = sanitize_log(username)
  logger.info("Login attempt for user: #{safe_name}") # SAFE
end

def log_request(logger, ip, path)
  logger.warn("Request from #{sanitize_log(ip)} to #{sanitize_log(path)}") # SAFE
end
