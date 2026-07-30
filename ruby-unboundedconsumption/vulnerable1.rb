# No max_tokens cap — user can trigger arbitrarily long (expensive) completions
require 'openai'

class AiController < ApplicationController
  def complete
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o',
        # VULNERABLE — no max_tokens; model may generate thousands of tokens
        messages: [{ role: 'user', content: params[:prompt] }]
      }
    )
    render json: { response: resp.dig('choices', 0, 'message', 'content') }
  end
end
