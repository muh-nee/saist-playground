# Financial recommendations from LLM with no risk disclaimer
require 'openai'

class FinanceController < ApplicationController
  def recommend
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:      'gpt-4o',
        max_tokens: 256,
        messages:   [{ role: 'user', content: "Should I invest in #{params[:asset]}?" }]
      }
    )
    recommendation = resp.dig('choices', 0, 'message', 'content')
    # VULNERABLE — financial advice served without any risk warning or fact-checking
    render json: { recommendation: recommendation }
  end
end
