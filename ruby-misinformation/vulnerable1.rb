# LLM factual output rendered directly with no disclaimer — medical advice
require 'openai'

class MedicalBot
  def answer(question)
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:      'gpt-4o',
        max_tokens: 512,
        messages:   [{ role: 'user', content: question }]
      }
    )
    # VULNERABLE — presenting LLM output as authoritative medical fact with no disclaimer
    resp.dig('choices', 0, 'message', 'content')
  end
end

class HealthController < ApplicationController
  def advise
    bot    = MedicalBot.new
    answer = bot.answer(params[:question])
    render json: { advice: answer } # VULNERABLE — no disclaimer added
  end
end
