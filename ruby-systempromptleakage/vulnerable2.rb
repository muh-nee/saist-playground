# System prompt logged to a publicly-accessible endpoint
require 'openai'

SYSTEM_PROMPT = "Internal use only. API key rotation schedule: every 30 days. Admin password hint: first pet's name + birth year."

class DebugController < ApplicationController
  def logs
    # VULNERABLE — system prompt exposed in debug log endpoint
    render json: { system_prompt: SYSTEM_PROMPT, env: ENV.slice('OPENAI_API_KEY', 'SECRET') }
  end
end

class ChatController < ApplicationController
  def ask
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          { role: 'system', content: SYSTEM_PROMPT },
          { role: 'user',   content: params[:q] }
        ]
      }
    )
    # VULNERABLE — include full message history (including system) in response
    render json: { messages: resp['choices'], prompt_used: SYSTEM_PROMPT }
  end
end
