# Sensitive data stored in a plain (non-encrypted) cookie
class TrackingController < ApplicationController
  def set_tracking
    user_id = current_user.id
    # VULNERABLE — sensitive user_id in a plaintext cookie with no protection
    cookies[:uid] = { value: user_id.to_s }
    render json: { tracking_set: true }
  end
end
