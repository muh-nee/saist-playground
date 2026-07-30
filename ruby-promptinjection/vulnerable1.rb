# User input interpolated directly into the LLM prompt — prompt injection
require 'openai'

class TranslationController < ApplicationController
  def translate
    text     = params[:text]
    language = params[:language]
    client   = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp     = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          # VULNERABLE — user controls the entire message string, including the instruction
          { role: 'user', content: "Translate the following to #{language}: #{text}" }
        ]
      }
    )
    render json: { translation: resp.dig('choices', 0, 'message', 'content') }
  end
end
