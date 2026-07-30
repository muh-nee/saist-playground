# redirect_to with unvalidated user-supplied URL — open redirect
class SessionsController < ApplicationController
  def create
    # ... authenticate user ...
    redirect_to params[:return_to] # VULNERABLE — redirects to any URL the attacker provides
  end

  def oauth_callback
    url = params[:redirect_url]
    redirect_to url # VULNERABLE
  end
end
