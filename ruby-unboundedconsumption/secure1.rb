# Hardcoded max_tokens cap — never user-controlled
require 'openai'

MAX_TOKENS = 512 # SAFE — hard server-side cap

class AiController < ApplicationController
  def complete
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:      'gpt-4o',
        max_tokens: MAX_TOKENS, # SAFE — fixed cap regardless of user request
        messages:   [{ role: 'user', content: params[:prompt] }]
      }
    )
    render json: { response: resp.dig('choices', 0, 'message', 'content') }
  end
end
