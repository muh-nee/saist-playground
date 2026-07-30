# Encrypted signed cookie via Rails cookie store — best practice
class AuthController < ApplicationController
  def login
    user = User.find_by(email: params[:email])
    return render json: { error: 'unauthorized' }, status: 401 unless user&.authenticate(params[:password])

    # SAFE — Rails encrypts and signs cookies[:session]; also configure
    # config.force_ssl = true and config.session_store :cookie_store, secure: true
    cookies.encrypted[:session] = {
      value:     { user_id: user.id },
      expires:   8.hours.from_now,
      secure:    true,
      httponly:  true,
      same_site: :lax  # SAFE — lax allows top-level navigation while blocking CSRF
    }
    render json: { ok: true }
  end
end
