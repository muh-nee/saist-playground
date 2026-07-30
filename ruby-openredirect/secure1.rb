# Restrict redirects to internal paths only
class SessionsController < ApplicationController
  ALLOWED_HOSTS = %w[example.com www.example.com].freeze

  def create
    # ... authenticate user ...
    return_to = params[:return_to].to_s

    # SAFE — only allow relative paths (no scheme/host)
    if return_to.start_with?('/') && !return_to.start_with?('//')
      redirect_to return_to
    else
      redirect_to root_path
    end
  end

  def oauth_callback
    url = params[:redirect_url].to_s
    uri = URI.parse(url)
    # SAFE — validate host against allowlist before redirecting
    if ALLOWED_HOSTS.include?(uri.host)
      redirect_to url
    else
      redirect_to root_path
    end
  rescue URI::InvalidURIError
    redirect_to root_path
  end
end
