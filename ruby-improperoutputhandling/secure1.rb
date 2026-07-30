# Render LLM output as plain text — never mark it html_safe
require 'openai'

class AiController < ApplicationController
  def generate
    client   = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    response = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [{ role: 'user', content: params[:prompt] }]
      }
    )
    llm_output = response.dig('choices', 0, 'message', 'content').to_s
    # SAFE — render as JSON string; client displays as text, not HTML
    render json: { response: llm_output }
  end
end
