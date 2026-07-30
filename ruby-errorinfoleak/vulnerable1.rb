# Full exception message and backtrace returned to the client
class ApiController < ApplicationController
  def create
    result = DataService.process(params[:data])
    render json: result
  rescue => e
    # VULNERABLE — internal stack trace exposed to external clients
    render json: { error: e.message, backtrace: e.backtrace }, status: 500
  end
end
