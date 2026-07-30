# Keep user input in the user role only — never interpolate into system prompt
require 'openai'

class TranslationController < ApplicationController
  ALLOWED_LANGUAGES = %w[English Spanish French German Japanese Chinese].freeze

  def translate
    text     = params[:text].to_s.slice(0, 2000)
    language = params[:language].to_s

    # SAFE — validate language against allowlist
    return render json: { error: 'Unsupported language' }, status: 422 unless ALLOWED_LANGUAGES.include?(language)

    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          # SAFE — instruction is fixed in the system message; user text is in the user role
          { role: 'system', content: "You are a translator. Translate the user's message to #{language}. Output only the translation." },
          { role: 'user',   content: text }
        ]
      }
    )
    render json: { translation: resp.dig('choices', 0, 'message', 'content') }
  end
end
