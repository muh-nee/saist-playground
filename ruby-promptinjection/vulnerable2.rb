require 'openai'

class ChatbotController < ApplicationController
  def chat
    user_name = params[:name]
    client    = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp      = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          { role: 'system', content: "You are a helpful assistant. The user's name is #{user_name}. Greet them." },
          { role: 'user',   content: params[:message] }
        ]
      }
    )
    render json: { reply: resp.dig('choices', 0, 'message', 'content') }
  end
end
