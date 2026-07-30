# Exception details returned in HTTP response headers and body
class IntegrationController < ApplicationController
  def call_service
    response = ExternalService.call(params[:endpoint])
    render json: response
  rescue Net::HTTPError => e
    response.headers['X-Error-Detail'] = e.message # VULNERABLE — header exposure
    render plain: "Error: #{e.class}: #{e.message}\n#{e.backtrace.first(5).join("\n")}", status: 500
    # VULNERABLE — full exception info in response body
  end
end
