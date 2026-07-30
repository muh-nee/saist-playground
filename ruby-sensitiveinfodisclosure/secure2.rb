# Health endpoint that reveals nothing sensitive
class HealthController < ApplicationController
  def status
    # SAFE — only expose operational status, no internal details
    render json: { status: 'ok', version: AppVersion::STRING }
  end

  def config_check
    # SAFE — verify config is present without revealing values
    render json: {
      db_configured:     ENV.key?('DATABASE_URL'),
      stripe_configured: ENV.key?('STRIPE_SECRET_KEY')
    }
  end
end
