require 'openai'

class SummaryController < ApplicationController
  def index
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    response = client.chat(
      parameters: {
        model: 'gpt-4o-mini',
        messages: [{ role: 'user', content: "Summarize the latest AI news in Markdown." }]
      }
    )
    content = response.dig('choices', 0, 'message', 'content')
    headers['Content-Security-Policy'] = "default-src 'self'; img-src *"
    render json: { content: content }
  end
end
