# System prompt is kept server-side; only the model's answer is returned
require 'openai'

SYSTEM_PROMPT = "You are a helpful assistant. Company policies are confidential."

class ChatController < ApplicationController
  def message
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o-mini',
        messages: [
          { role: 'system', content: SYSTEM_PROMPT },
          { role: 'user',   content: params[:message] }
        ]
      }
    )
    answer = resp.dig('choices', 0, 'message', 'content')
    # SAFE — only the model's answer is returned; system prompt is never sent to client
    render json: { reply: answer }
  end
end
