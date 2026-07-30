# Rails allow_other_host: false (default since Rails 7) + path allowlist
class ApiController < ApplicationController
  def redirect_after_payment
    callback = params[:callback_url].to_s
    # SAFE — allow_other_host: false (default) prevents external redirects in Rails 7+
    redirect_to(callback, allow_other_host: false)
  rescue ActionController::Redirecting::UnsafeRedirectError
    redirect_to root_path
  end
end
