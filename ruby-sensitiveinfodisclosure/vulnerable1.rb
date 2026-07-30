# Rendering secrets and credentials in API responses
class ConfigController < ApplicationController
  def show
    render json: {
      db_url:     ENV['DATABASE_URL'], # VULNERABLE
      secret_key: ENV['SECRET_KEY_BASE'], # VULNERABLE
      api_token:  ENV['STRIPE_SECRET_KEY'] # VULNERABLE
    }
  end

  def debug
    render json: {
      env:    ENV.to_h, # VULNERABLE — entire environment
      config: Rails.application.config.to_h
    }
  end
end
