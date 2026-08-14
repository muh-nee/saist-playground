require 'openai'

class MemoryController < ApplicationController
  def store_summary
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])

    response = client.chat(
      parameters: {
        model: 'gpt-4o',
        messages: [{ role: 'user', content: params[:query] }]
      }
    )

    llm_output = response.dig('choices', 0, 'message', 'content')

    sanitized = llm_output
      .gsub(/ignore (all |previous )?instructions?/i, '')
      .gsub(/you are now/i, '')
      .gsub(/system:/i, '')
      .gsub(/<\|[^|]*\|>/, '')
      .strip

    VectorStore.add_texts([sanitized])

    render json: { status: 'stored' }
  end
end
