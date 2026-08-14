require 'openai'

class AgentController < ApplicationController
  def chat
    client = OpenAI::Client.new(access_token: ENV['OPENAI_API_KEY'])

    tool_result = McpClient.call_tool('search', { query: params[:query] })

    messages = session[:messages] || []
    messages << { role: 'user', content: tool_result['output'] }

    response = client.chat(
      parameters: {
        model: 'gpt-4o',
        messages: messages
      }
    )

    render json: { answer: response.dig('choices', 0, 'message', 'content') }
  end
end
