# User controls max_tokens directly — can request max possible output
require 'openai'

class AiController < ApplicationController
  def generate
    client     = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    max_tokens = params[:max_tokens].to_i # VULNERABLE — attacker sets this to 128000
    resp       = client.chat(
      parameters: {
        model:      'gpt-4o',
        max_tokens: max_tokens, # VULNERABLE — user-controlled token limit
        messages:   [{ role: 'user', content: params[:prompt] }]
      }
    )
    render json: { response: resp.dig('choices', 0, 'message', 'content') }
  end
end
