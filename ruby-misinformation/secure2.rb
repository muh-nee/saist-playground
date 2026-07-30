# Human review queue before publishing LLM-generated articles
require 'openai'

FINANCIAL_DISCLAIMER = "AI-generated content. Not financial advice. Past performance does not guarantee future results."

class NewsGenerator
  def draft_article(topic)
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(
      parameters: {
        model:    'gpt-4o',
        messages: [
          { role: 'system', content: 'Draft a factual news article for human review.' },
          { role: 'user',   content: "Write about: #{topic}" }
        ]
      }
    )
    content = resp.dig('choices', 0, 'message', 'content')
    # SAFE — save as draft pending human review; do NOT auto-publish
    Article.create!(title: topic, body: content, published: false, review_status: 'pending')
  end
end

class FinanceController < ApplicationController
  def recommend
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])
    resp   = client.chat(parameters: { model: 'gpt-4o', max_tokens: 256,
                                       messages: [{ role: 'user', content: "General information about #{params[:asset]}?" }] })
    render json: { response: resp.dig('choices', 0, 'message', 'content'),
                   disclaimer: FINANCIAL_DISCLAIMER } # SAFE — disclaimer always present
  end
end
