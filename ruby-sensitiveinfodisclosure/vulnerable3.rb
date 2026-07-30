# Internal IP and server details exposed in headers and body
class HealthController < ApplicationController
  def status
    render json: {
      hostname:     Socket.gethostname,    # VULNERABLE — internal hostname
      server_ip:    Socket.ip_address_list.map(&:ip_address), # VULNERABLE
      ruby_version: RUBY_VERSION,
      rails_env:    Rails.env,
      db_adapter:   ActiveRecord::Base.connection.adapter_name
    }
  end
end
