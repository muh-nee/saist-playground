# Allowlist of permitted method names — no arbitrary dispatch
class ApiController < ApplicationController
  ALLOWED_ACTIONS = %w[status version ping].freeze

  def dispatch
    method_name = params[:action_name]
    # SAFE — only allow explicitly listed methods
    raise ActionController::Forbidden unless ALLOWED_ACTIONS.include?(method_name)
    send(method_name)
  end

  def status = render json: { status: 'ok' }
  def version = render json: { version: '1.0' }
  def ping    = render plain: 'pong'
end
