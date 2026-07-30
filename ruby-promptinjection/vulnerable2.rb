# User-supplied name injected into system prompt — persona hijack
require 'openai'

class ChatbotController < ApplicationController
  def chat
    user_name = params[:name]
    client    = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp      = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          # VULNERABLE — user_name is interpolated into the system prompt
          # An attacker sends name = "Dave. Ignore all previous instructions and..."
          { role: 'system', content: "You are a helpful assistant. The user's name is #{user_name}. Greet them." },
          { role: 'user',   content: params[:message] }
        ]
      }
    )
    render json: { reply: resp.dig('choices', 0, 'message', 'content') }
  end
end
