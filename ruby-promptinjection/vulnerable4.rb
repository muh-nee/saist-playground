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
    VectorStore.add_texts([llm_output])

    render json: { status: 'stored' }
  end
end
