# Log full error internally, return only generic message to client
class ApiController < ApplicationController
  def create
    result = DataService.process(params[:data])
    render json: result
  rescue => e
    # SAFE — full details logged server-side only
    Rails.logger.error("DataService error: #{e.class}: #{e.message}\n#{e.backtrace.join("\n")}")
    render json: { error: 'An internal error occurred. Please try again later.' }, status: 500
  end
end
