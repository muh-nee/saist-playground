# Open redirect via Location header set manually
class ApiController < ApplicationController
  def redirect_after_payment
    callback = params[:callback_url]
    # VULNERABLE — arbitrary external redirect via header
    response.headers['Location'] = callback
    head :found
  end

  def share
    url = params[:url]
    redirect_to url, allow_other_host: true # VULNERABLE — explicitly permits any host
  end
end
