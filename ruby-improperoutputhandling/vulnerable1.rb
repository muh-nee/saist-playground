# LLM response rendered as raw HTML — XSS via model output
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
    llm_output = response.dig('choices', 0, 'message', 'content')
    # VULNERABLE — LLM output injected into DOM without escaping
    render html: llm_output.html_safe
  end
end
