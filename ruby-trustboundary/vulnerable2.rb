# Trusting HTTP headers for authorization decisions
class ApiController < ApplicationController
  def index
    # VULNERABLE — X-User-Role is a request header set by the client
    role = request.headers['X-User-Role']
    raise "Forbidden" unless role == 'admin'
    render json: Admin.all
  end

  def set_permissions
    # VULNERABLE — permissions list comes from untrusted cookie
    perms = JSON.parse(cookies[:permissions] || '[]')
    render json: { allowed: perms.include?('write') }
  end
end
